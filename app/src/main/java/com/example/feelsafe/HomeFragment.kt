package com.example.feelsafe

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var adapter1 : InviteMemberAdapter
    private val listContacts : ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberModel>(
            MemberModel("Shubham",
                "9th buildind, 2nd floor, Maldiv road, Manali 9th buildind",
                "80%",
                "210"),
            MemberModel("Ansh",
                "5th buildind, 3rd floor, Maldiv road, Manali 9th buildind",
                "50%",
                "210"),
            MemberModel("Roop",
                "10th buildind, 4th floor, Maldiv road, Manali 9th buildind",
                "70%",
                "210"),
        )

        val adapter = ItemMemberAdapter(listMembers)
        val recycler = requireView().findViewById<RecyclerView>(R.id.rView)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter



        Log.d("fetchContacts89", "fetchContacts:start hogaya ")

        Log.d("fetchContacts89", "fetchContacts:start hogaya ${listContacts.size}")
        adapter1 = InviteMemberAdapter(listContacts)
        fetchDatabaseContacts()
        Log.d("fetchContacts89", "fetchContacts:end hogaya ")


        CoroutineScope(Dispatchers.IO).launch {

            Log.d("fetchContacts89", "fetchContacts:Coroutines start hogaya ")

            insertDatabaseContacts(fetchContacts())


            Log.d("fetchContacts89", "fetchContacts:Coroutines end hogaya ${listContacts.size}")
        }

        val recycler1 = requireView().findViewById<RecyclerView>(R.id.member_invite)
        recycler1.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        recycler1.adapter = adapter1
    }

    private fun fetchDatabaseContacts() {
        val database = MyFamilyDataBase.myDatabase(requireContext())

        database.contactDao().getAllContacts().observe(viewLifecycleOwner){

            Log.d("fetchContact89", "fetchDatabaseContacts: ")
            listContacts.clear()

            listContacts.addAll(it)
            adapter1.notifyDataSetChanged()

        }

    }

    private suspend fun insertDatabaseContacts(listContacts: ArrayList<ContactModel>) {
        val database = MyFamilyDataBase.myDatabase(requireContext())

        database.contactDao().insertAll(listContacts)
    }


    private fun fetchContacts(): ArrayList<ContactModel> {
        Log.d("fetchContacts69", "fetchContacts:start ")
        val cr = requireActivity().contentResolver
        val cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        val listContacts:ArrayList<ContactModel> = ArrayList()
        if(cursor!=null && cursor.count > 0){
            while(cursor.moveToNext()){

                val id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNo = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))


                if(hasPhoneNo > 0.toString()){
                    val pcur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
                        arrayOf(id),
                        ""
                    )
                    if(pcur!=null && pcur.count >= 0){
                        while (pcur.moveToNext()){
                            val phonenum = pcur.getString(pcur.getColumnIndexOrThrow(
                                ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContacts.add(ContactModel(name,phonenum))
                        }
                        pcur.close()
                    }
                }
            }
            cursor.close()

        }

        return listContacts
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}