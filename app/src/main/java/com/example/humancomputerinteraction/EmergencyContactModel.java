package com.example.humancomputerinteraction;

public class EmergencyContactModel
{
    private final Integer iconId;
    private final String emergency_contact_name;
    private final String emergency_contact_number;

    public EmergencyContactModel(Integer iconId, String emergency_contact_name, String emergency_contact_number)
    {
        this.iconId = iconId;
        this.emergency_contact_name = emergency_contact_name;
        this.emergency_contact_number = emergency_contact_number;
    }

    public Integer getIconId()
    {
        return iconId;
    }

    public String getEmergency_contact_name()
    {
        return emergency_contact_name;
    }

    public String getEmergency_contact_number()
    {
        return emergency_contact_number;
    }
}
