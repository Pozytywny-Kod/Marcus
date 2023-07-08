package pl.grygol.projectmarcus.interfaces

interface Navigable {
    enum class Destination {
        MainDashboard, Dashboard, ProjectDetails, NewExpense, ExpenseDetails, Camera
    }

    fun navigate(to: Destination)
}