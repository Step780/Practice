package com.example.practice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_second.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_second, container, false)
        val cityListView = root.findViewById<ListView>(R.id.listView)
        //cityListView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha)) Анимация
        cityListView.adapter = ArrayAdapter<String>(requireContext(), R.layout.text_view, resources.getStringArray(R.array.cities))
        cityListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val contains = sharedPref.contains("city")
            val city = parent.getItemAtPosition(position) as String
            Toast.makeText(context, "Выбран город: $city", Toast.LENGTH_SHORT).show()
            with(sharedPref.edit()) {
                putString("city", city)
//                if (position == 1) // для дебага
//                    remove("city")
                apply()

               // cityListView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale))
               // view.findViewById<TextView>(R.id.listTextView).startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale))
            }
            if (!contains)
                parentFragmentManager.beginTransaction().replace(R.id.fragment, FirstFragment()).commit()}
        return root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}