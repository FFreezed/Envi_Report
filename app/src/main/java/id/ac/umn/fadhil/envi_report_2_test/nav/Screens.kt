package id.ac.umn.fadhil.envi_report_2_test.nav

sealed class Screens(val route: String) {
    object MainScreen: Screens(route = "main_screen")
    object GetDataScreen: Screens(route = "get_data_screen")
    object AddDataScreen: Screens(route = "add_data_screen")
    object ListDataScreen: Screens(route = "list_data_screen")
    object ProfileScreen: Screens(route = "profile_screen")
    object SignInScreen: Screens(route = "sign_in_screen")
//    object DetailedDataScreen: Screens(route = "detailed_data_screen")
}
