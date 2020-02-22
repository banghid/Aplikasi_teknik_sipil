package com.example.aplikasipembelajarantekniksipil.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasipembelajarantekniksipil.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var userAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAuth = FirebaseAuth.getInstance()
        if (userAuth.currentUser?.displayName != null){
            profile_name.text = userAuth.currentUser?.displayName.toString()
        }else profile_name.text = "display name not set"

        profile_email.text = userAuth.currentUser?.email
    }


}
