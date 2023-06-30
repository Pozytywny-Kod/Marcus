package pl.grygol.projectmarcus.interfaces

interface Navigable {
    enum class Destination {
        Dashboard, ProjectDetails
    }

    fun navigate(to: Destination)
}