package co.edu.usbcali.simulador.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import co.edu.usbcali.simulador.R
import co.edu.usbcali.simulador.database.AppDatabase
import co.edu.usbcali.simulador.database.user.User

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PasswordChangeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PasswordChangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswordChangeFragment : Fragment() {

    private var DB: AppDatabase? = null
    private var loggedUser: User? = null
    private var currentPasswordEditText: EditText? = null
    private var newPasswordEditText: EditText? = null
    private var passwordConfirmationEditText: EditText? = null
    private var changePasswordButton: Button? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB = AppDatabase.getAppDatabase(context)
        if (arguments != null) {
            this.loggedUser = arguments.getParcelable("loggedUser")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_password_change, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPasswordEditText = view!!.findViewById(R.id.editTextOldPassword)
        newPasswordEditText = view!!.findViewById(R.id.editTextNewPassword)
        passwordConfirmationEditText = view!!.findViewById(R.id.editTextPasswordConfirmation)
        changePasswordButton = view!!.findViewById(R.id.buttonCreateProduct)
        changePasswordButton!!.setOnClickListener {
            var currentPassword = currentPasswordEditText!!.text.toString()
            var newPassword = newPasswordEditText!!.text.toString()
            var passwordConfirmation = passwordConfirmationEditText!!.text.toString()
            if (loggedUser != null) {
                if (loggedUser!!.password == currentPassword) {
                    if (newPassword == passwordConfirmation) {
                        var userDao = DB!!.userDao()
                        loggedUser!!.password = newPassword
                        userDao.update(loggedUser)
                        Toast.makeText(context, "Se actualizo la contraseña correctamente", Toast.LENGTH_SHORT).show()
                        fragmentManager.popBackStackImmediate()
                    }
                } else {
                    Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private var loggedUser = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param loggedUser Parameter 1.
         * @return A new instance of fragment PasswordChangeFragment.
         */
        fun newInstance(loggedUser: User): PasswordChangeFragment {
            val fragment = PasswordChangeFragment()
            val args = Bundle()
            args.putParcelable("loggedUser", loggedUser)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
