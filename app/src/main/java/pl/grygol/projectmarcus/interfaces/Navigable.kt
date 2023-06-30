package pl.grygol.projectmarcus.interfaces

interface Navigable {
    enum class Destination {
        Dashboard, ProjectDetails, NewExpense
    }

    fun navigate(to: Destination)
}