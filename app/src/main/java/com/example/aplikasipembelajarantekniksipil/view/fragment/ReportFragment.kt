package com.example.aplikasipembelajarantekniksipil.view.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.StageModel
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel
import com.example.aplikasipembelajarantekniksipil.presenter.ChapterPresenter
import com.example.aplikasipembelajarantekniksipil.presenter.KnowledgePresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.ChapterView
import com.example.aplikasipembelajarantekniksipil.view.view_interface.KnowledgeView
import com.github.lzyzsd.circleprogress.DonutProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_report.*


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment(), ChapterView, KnowledgeView {

    private var knowledgeData: ArrayList<KnowledgeModel> = arrayListOf()
    private var chaptersData: ArrayList<ChapterModel> = arrayListOf()
    private var size: ArrayList<Int> = arrayListOf()
    private lateinit var chapterPresenter: ChapterPresenter
    private lateinit var knowledgePresenter: KnowledgePresenter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var kayuProgress: ArrayList<StageModel> = arrayListOf()
    private var betonProgress: ArrayList<StageModel> = arrayListOf()
    private var bajaProgress: ArrayList<StageModel> = arrayListOf()
    private var totalKnowledge = 0
    private lateinit var donutProgress: DonutProgress
    private var totalProgress = 0
    private var totalKayuProggress = 0
    private var totalBetonProggress = 0
    private var totalBajaProggress = 0
    private var totalNilaiKeseluruhan = 0
    private var totalQuizKeseluruhan = 0
    private var totalQuizKayu = 0
    private var totalQuizBeton = 0
    private var totalQuizBaja = 0
    private var totalNilaiQuizKayu = 0
    private var progressKayu:Float = 0f
    private var progressBaja:Float = 0f
    private var progressBeton:Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseAccess = DatabaseAccess.getInstance(view.context)
        mAuth = FirebaseAuth.getInstance()
        donutProgress = donut_progress

        databaseReference = FirebaseDatabase.getInstance().reference
        chapterPresenter = ChapterPresenter(this)
        knowledgePresenter = KnowledgePresenter(this)
        chapterPresenter.setChapter(databaseAccess)
        knowledgePresenter.getKnowledgeWhere(1, databaseAccess)
        size.add(knowledgeData.size)
        totalKnowledge += size[0]
        knowledgePresenter.getKnowledgeWhere(2, databaseAccess)
        size.add(knowledgeData.size)
        totalKnowledge += size[1]
        knowledgePresenter.getKnowledgeWhere(3, databaseAccess)
        size.add(knowledgeData.size)
        totalKnowledge += size[2]

        icon_kayu.setImageBitmap(chaptersData[0].chapterImage)
        icon_beton.setImageBitmap(chaptersData[1].chapterImage)
        icon_baja.setImageBitmap(chaptersData[2].chapterImage)

        knowledgePresenter.getAll(databaseAccess)
        var j = 0
        while (j < knowledgeData.size) {
            if (knowledgeData[j].knowledgeQuiz == "yes") {
                if (knowledgeData[j].chapterId == 1) {
                    totalQuizKayu++
                }
                if (knowledgeData[j].chapterId == 1) {
                    totalQuizBeton++
                }
                if (knowledgeData[j].chapterId == 1) {
                    totalQuizBaja++
                }
            }
            j++
        }

        Log.d(
            ">>>>>ReportFragment",
            "total_quiz: $totalQuizKeseluruhan , total_quiz_kayu: $totalQuizKayu"
        )

        databaseReference.child("save_state")
            .child(mAuth.currentUser?.uid.toString())
            .child("1")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (noteDataSnapshot in p0.children) {
                        val request: StageModel? = noteDataSnapshot.getValue(StageModel::class.java)

                        kayuProgress.add(request!!)
                        totalKayuProggress = kayuProgress.size
                        totalProgress =
                            totalBajaProggress + totalKayuProggress + totalBetonProggress

                    }

                    donutProgress.progress =
                        (totalProgress.toFloat() / totalKnowledge.toFloat()) * 100
                    progressKayu = (totalKayuProggress.toFloat() / size[0].toFloat()) * 100
                    progress_total_kayu.progress = progressKayu.toInt()

                    var i = 0
                    var progressQuiz = 0

                    while (i < kayuProgress.size) {
                        Log.d(">>>>>ReportFragment", kayuProgress[i].score)
                        if (kayuProgress[i].score != "kosong") {
                            progressQuiz++
                            totalNilaiQuizKayu += kayuProgress[i].score.toInt()
                            totalNilaiKeseluruhan += kayuProgress[i].score.toInt()
                            totalQuizKeseluruhan++
                        }
                        i++
                    }
                    Log.d(
                        ">>>>>ReportFragment",
                        "progressQuiz: " + progressQuiz +
                                ", totalNilaiQuiz: " + totalNilaiQuizKayu +
                                ", totalNilaiKeseluruhan:" + totalNilaiKeseluruhan
                    )

                    val rataRata = (totalNilaiQuizKayu.toFloat() / progressQuiz.toFloat())
                    Log.d(
                        ">>>>>ReportFragment",
                        "rata-rata kayu: $rataRata"
                    )
                    val rataKeseluruhan =
                        (totalNilaiKeseluruhan.toFloat() / totalQuizKeseluruhan.toFloat())
                    Log.d(
                        ">>>>>ReportFragment",
                        "rata-rata keseluruhan: $rataKeseluruhan"
                    )
                    val progressPercentage = (progressQuiz.toFloat()/totalQuizKayu.toFloat())*100
                    progress_quiz_kayu.progress = progressPercentage.toInt()
                    rata_quiz_keseluruhan.progress = rataKeseluruhan.toInt()
                    rata_quiz_kayu.progress = rataRata.toInt()
                }

            })

        databaseReference.child("save_state")
            .child(mAuth.currentUser?.uid.toString())
            .child("2")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (noteDataSnapshot in p0.children) {
                        val request: StageModel? = noteDataSnapshot.getValue(StageModel::class.java)

                        betonProgress.add(request!!)
                        totalBetonProggress = betonProgress.size
                        totalProgress =
                            totalBajaProggress + totalKayuProggress + totalBetonProggress

                    }

                    donutProgress.progress =
                        (totalProgress.toFloat() / totalKnowledge.toFloat()) * 100
                    progressBeton = (totalBetonProggress.toFloat() / size[1].toFloat()) * 100
                    progress_total_beton.progress = progressBeton.toInt()

                    var i = 0
                    var progressQuiz = 0
                    var totalNilaiQuiz = 0
                    while (i < betonProgress.size) {
                        if (betonProgress[i].score != "kosong") {
                            progressQuiz++
                            totalNilaiQuiz += betonProgress[i].score.toInt()
                            totalNilaiKeseluruhan += betonProgress[i].score.toInt()
                            totalQuizKeseluruhan++
                        }
                        i++
                    }

                    val rataRata = (totalNilaiQuiz.toFloat() / progressQuiz.toFloat())
                    val rataKeseluruhan =
                        (totalNilaiKeseluruhan.toFloat() / totalQuizKeseluruhan.toFloat())
                    val progressPercentage = (progressQuiz.toFloat()/totalQuizBeton.toFloat())*100
                    progress_quiz_beton.progress = progressPercentage.toInt()
                    rata_quiz_keseluruhan.progress = rataKeseluruhan.toInt()
                    rata_quiz_beton.progress = rataRata.toInt()
                }

            })

        databaseReference.child("save_state")
            .child(mAuth.currentUser?.uid.toString())
            .child("3")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (noteDataSnapshot in p0.children) {
                        val request: StageModel? = noteDataSnapshot.getValue(StageModel::class.java)

                        bajaProgress.add(request!!)
                        totalBajaProggress = bajaProgress.size
                        totalProgress =
                            totalBajaProggress + totalKayuProggress + totalBetonProggress

                    }

                    donutProgress.progress =
                        (totalProgress.toFloat() / totalKnowledge.toFloat()) * 100
                    progressBaja = (totalBajaProggress.toFloat() / size[2].toFloat()) * 100
                    progress_total_baja.progress = progressBaja.toInt()

                    var i = 0
                    var progressQuiz = 0
                    var totalNilaiQuiz = 0
                    while (i < bajaProgress.size) {
                        if (bajaProgress[i].score != "kosong") {
                            progressQuiz++
                            totalNilaiQuiz += bajaProgress[i].score.toInt()
                            totalNilaiKeseluruhan += bajaProgress[i].score.toInt()
                            totalQuizKeseluruhan++
                        }
                        i++
                    }

                    val rataRata = (totalNilaiQuiz.toFloat() / progressQuiz.toFloat())
                    val rataKeseluruhan =
                        (totalNilaiKeseluruhan.toFloat() / totalQuizKeseluruhan.toFloat())
                    val progressPercentage = (progressQuiz.toFloat()/totalQuizBaja.toFloat())*100
                    progress_quiz_baja.progress = progressPercentage.toInt()
                    rata_quiz_keseluruhan.progress = rataKeseluruhan.toInt()
                    rata_quiz_baja.progress = rataRata.toInt()
                }

            })

    }

    override fun showChapter(chapters: List<ChapterModel>) {
        chaptersData.clear()
        chaptersData.addAll(chapters)
    }

    override fun showKnowledge(knowledges: List<KnowledgeModel>) {
        knowledgeData.clear()
        knowledgeData.addAll(knowledges)
    }

    override fun loadUserStage(userStages: List<UserStageModel>) {

    }
}
