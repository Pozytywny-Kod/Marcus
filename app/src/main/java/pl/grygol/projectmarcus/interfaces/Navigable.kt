package pl.grygol.projectmarcus.interfaces

interface Navigable {
    enum class Destination {
        Dashboard, ProjectDetails, NewExpense, ExpenseDetails
    }

    fun navigate(to: Destination)
}