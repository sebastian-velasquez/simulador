package co.edu.usbcali.simulador.products

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import co.edu.usbcali.simulador.R
import co.edu.usbcali.simulador.database.AppDatabase
import co.edu.usbcali.simulador.database.account.Account
import co.edu.usbcali.simulador.database.user.User

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateProductFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {

    private var DB: AppDatabase? = null
    private var loggedUser: User? = null
    private var buttonCreateProduct : Button? = null
    private var radioButtonAccountType1: RadioButton? = null
    private var radioButtonAccountType2: RadioButton? = null
    private var netBalanceEditText: EditText? = null


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
        return inflater!!.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCreateProduct = view!!.findViewById(R.id.buttonCreateProduct)
        radioButtonAccountType1 = view!!.findViewById(R.id.radioButtonAccountType1)
        radioButtonAccountType2 = view!!.findViewById(R.id.radioButtonAccountType2)
        netBalanceEditText = view!!.findViewById(R.id.editTextNetBalance)
        buttonCreateProduct!!.setOnClickListener({
            val accountDao = DB!!.accountDao()
            var netBalance = netBalanceEditText!!.text.toString().toDouble()
            var accountType = if (radioButtonAccountType1!!.isChecked) 1 else 2
            var newAccount = Account()
            newAccount.netBalance = netBalance
            newAccount.type = accountType
            newAccount.userId = loggedUser!!.id
            accountDao.insert(newAccount)
            Toast.makeText(context, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
            fragmentManager.popBackStackImmediate()
        })
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
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
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
         * @return A new instance of fragment ProfileFragment.
         */
        fun newInstance(loggedUser: User): CreateProductFragment {
            val fragment = CreateProductFragment()
            val args = Bundle()
            args.putParcelable("loggedUser", loggedUser)
            fragment.arguments = args
            return fragment
        }
    }
}
