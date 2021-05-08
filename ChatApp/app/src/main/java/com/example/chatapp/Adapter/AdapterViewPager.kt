package com.example.chatapp.Adapter



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AdapterViewPager(manager: FragmentManager):FragmentPagerAdapter(manager) {

    private var fragmentList:ArrayList<Fragment> = ArrayList()
    private var titleList:ArrayList<String> = ArrayList()

    override fun getCount(): Int {
       return fragmentList.size

    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]


    }
    fun addFragment(fragment: Fragment,title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}