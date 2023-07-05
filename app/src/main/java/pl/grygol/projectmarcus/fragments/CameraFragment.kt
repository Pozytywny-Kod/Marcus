
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.grygol.projectmarcus.databinding.ActivityCameraBinding
import pl.grygol.projectmarcus.interfaces.Navigable

class CameraFragment : Fragment() {
    private lateinit var binding: ActivityCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cameraCaptureButton.setOnClickListener {
            //take photo
            Toast.makeText(context,"Photo taken",Toast.LENGTH_SHORT).show()
        }
        binding.cancelButton.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.Dashboard)
        }
        binding.flashButton.setOnClickListener {
            //turn on flashlight
        }
        binding.skipButton.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.NewExpense)
        }

    }
}
