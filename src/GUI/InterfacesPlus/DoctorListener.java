package GUI.InterfacesPlus;

import BL.Doctor;
import BL.Enums;

public interface DoctorListener {
    public void doctorEmmited(Doctor doctor, Enums.BUTTON_TYPE clicked);
}
