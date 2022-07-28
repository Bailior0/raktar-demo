package com.example.raktarappjustui1.nav

import com.example.raktarappjustui1.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object List : BottomNavItem("Lista", R.drawable.ic_format_list_bulleted_24,"list")
    object Import: BottomNavItem("Bevételezés",R.drawable.ic_move_to_inbox_24,"import")
    object Stats: BottomNavItem("Statisztika",R.drawable.ic_monitoring_24,"stats")
    object Account: BottomNavItem("Fiók",R.drawable.ic_account_circle_24,"account")
}
