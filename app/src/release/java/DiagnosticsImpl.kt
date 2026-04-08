import android.os.Build
import com.example.e2eapp.Diagnostics

class DiagnosticsImpl : Diagnostics {
    override fun getDeviceDetails(): String = Build.BRAND + "\n" + Build.DEVICE + "\n" + Build.MODEL + "\n" + Build.DISPLAY + "\n"
}
