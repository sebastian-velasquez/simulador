package co.edu.usbcali.simulador.movements

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
import co.edu.usbcali.simulador.database.movement.Movement
import co.edu.usbcali.simulador.database.user.User

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateMovementFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateMovementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateMovementFragment : Fragment() {

    private var DB: AppDatabase? = null
    private var loggedUser: User? = null

    private var buttonCreateMovement: Button? = null
    private var destinationEditText: EditText? = null
    private var valueEditText: EditText? = null
    private var radioButtonMovementType1: RadioButton? = null
    private var radioButtonMovementType2: RadioButton? = null

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
        return inflater!!.inflate(R.layout.fragment_create_movement, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCreateMovement = view!!.findViewById(R.id.buttonCreateMovement)
        radioButtonMovementType1 = view!!.findViewById(R.id.radioButtonMovementType1)
        radioButtonMovementType2 = view!!.findViewById(R.id.radioButtonMovementType2)
        valueEditText = view!!.findViewById(R.id.editTextValue)
        destinationEditText = view!!.findViewById(R.id.editTextDestination)
        buttonCreateMovement!!.setOnClickListener({
            val movementDao = DB!!.movementDao()
            val accountDao = DB!!.accountDao()
            var value = if (valueEditText!!.text.toString() == "") 0.0 else valueEditText!!.text.toString().toDouble()
            var destination = if(destinationEditText!!.text.toString() == "") 0 else destinationEditText!!.text.toString().toInt()
            var movementType = if (radioButtonMovementType1!!.isChecked) 1 else 2
            if (value != 0.0 && destination != 0) {
                var account = accountDao.findById(destination)
                if (account != null) {
                    var newMovement = Movement()
                    if (movementType == 2 && account.netBalance - value < 0) {
                        Toast.makeText(context, "No tienes saldo suficiente en la cuenta", Toast.LENGTH_SHORT).show()
                    } else {
                        if (movementType == 1) account.netBalance += value else account.netBalance -= value
                        newMovement.destination = account.id
                        newMovement.value = value
                        newMovement.type = movementType
                        newMovement.userId = loggedUser!!.id
                        movementDao.insert(newMovement)
                        accountDao.update(account)
                        Toast.makeText(context, "Movimiento registrado correctamente", Toast.LENGTH_SHORT).show()
                        fragmentManager.popBackStackImmediate()
                    }
                } else {
                    Toast.makeText(context, "Cuenta no encontrada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
            }
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
         * @return A new instance of fragment ProfileFragment.
         */
        fun newInstance(loggedUser: User): CreateMovementFragment {
            val fragment = CreateMovementFragment()
            val args = Bundle()
            args.putParcelable("loggedUser", loggedUser)
            fragment.arguments = args
            return fragment
        }
    }
}
