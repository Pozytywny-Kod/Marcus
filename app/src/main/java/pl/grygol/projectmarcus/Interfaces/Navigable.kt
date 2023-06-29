package pl.grygol.projectmarcus.Interfaces

interface Navigable {
    enum class Destination {
        Dashboard, ProjectDetails
    }

    fun navigate(to: Destination)
}