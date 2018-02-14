package co.edu.usbcali.simulador.movements

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.edu.usbcali.simulador.R
import co.edu.usbcali.simulador.database.AppDatabase
import co.edu.usbcali.simulador.database.movement.Movement
import co.edu.usbcali.simulador.database.user.User

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TransfersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TransfersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransfersFragment : Fragment(), AdapterView.OnItemClickListener {

    private var DB: AppDatabase? = null
    private var loggedUser: User? = null

    private var listView: ListView? = null
    private var listItems: List<Movement>? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB = AppDatabase.getAppDatabase(context)
        if (arguments != null) {
            loggedUser = arguments.getParcelable("loggedUser")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_transfers, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movementDao = DB!!.movementDao()
        this.listItems = movementDao.getAllByUserId(loggedUser!!.id)
        val arrayAdapter = AccountAdapter()
        this.listView = view!!.findViewById<ListView>(R.id.listViewMovements)
        this.listView!!.adapter = arrayAdapter
        listView!!.onItemClickListener = this
        fab = view!!.findViewById(R.id.floatingButtonMovements)
        fab!!.setOnClickListener({
            if (loggedUser != null){
                val bundle = Bundle()
                bundle.putParcelable("loggedUser", loggedUser)
                val createProductFragment = CreateMovementFragment.Companion.newInstance(loggedUser!!)
                val fragmentManager = activity.supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, createProductFragment)
                transaction.addToBackStack(null)
                transaction.commit()
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

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(context, listItems!![p2].id.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private var loggedUser = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param loggedUser Parameter 1.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(loggedUser: User): TransfersFragment {
            val fragment = TransfersFragment()
            val args = Bundle()
            args.putParcelable("loggedUser", loggedUser)
            fragment.arguments = args
            return fragment
        }
    }

    inner class AccountAdapter : ArrayAdapter<Movement>(this.context,
            android.R.layout.simple_expandable_list_item_2, android.R.id.text1, listItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super.getView(position, convertView, parent)
            val tv1 = view!!.findViewById<TextView>(android.R.id.text1)
            val tv2 = view!!.findViewById<TextView>(android.R.id.text2)
            val movement = getItem(position)
            val movementType1 = context.resources.getString(R.string.movement_type_1)
            val movementType2 = context.resources.getString(R.string.movement_type_2)
            val movementType = if (movement.type == 1) movementType1 else movementType2
            tv1.text = "Movimiento #" + movement.id.toString() + " - " + movementType
            tv2.text = "Valor: $" + movement.value.toString()
            return view
        }
    }
}