package pl.grygol.projectmarcus.Interfaces

interface Navigable {
    enum class Destination {
        ProjectDetails
    }

    fun navigate(to: Destination)
}