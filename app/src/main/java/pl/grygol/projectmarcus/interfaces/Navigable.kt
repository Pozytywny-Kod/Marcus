package pl.grygol.projectmarcus.interfaces

interface Navigable {
    enum class Destination {
        Dashboard, ProjectDetails, NewExpense, ExpenseDetails, Camera
    }

    fun navigate(to: Destination)
}