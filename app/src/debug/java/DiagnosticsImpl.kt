import com.example.e2eapp.Diagnostics

class DiagnosticsImpl : Diagnostics {
    override fun getDeviceDetails(): String = "Restricted for debug builds"
}
