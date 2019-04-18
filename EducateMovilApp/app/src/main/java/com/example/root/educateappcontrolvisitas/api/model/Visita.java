package com.example.root.educateappcontrolvisitas.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.Date;

public class Visita implements Parcelable,Comparable {


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Visita createFromParcel(Parcel in) {
            return new Visita(in);
        }

        public Visita[] newArray(int size) {
            return new Visita[size];
        }
    };


      private String check_in;
      private String check_out;
      private double coordinates_lat_in;
      private double coordinates_lat_out;
      private double coordinates_lon_in;
      private  double coordinates_lon_out;
      private String date_planned;
      private int id;
      private String observation;
      private int requirement_id;
      private String requirement_reason;
      private int school_id;
      private String school_address;
      private String school_ambassador_in;
      private String school_amie;
      private String school_name;
      private String school_parish;
      private String school_reference;
      private String school_workday;
      private int state;
      private int type;
      private int user_id;
      private int user_type;
      private String username;
      private String user_first_name;
    private String user_last_name;

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getSchool_workday() {
        return school_workday;
    }

    public void setSchool_workday(String school_workday) {
        this.school_workday = school_workday;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public double getCoordinates_lat_in() {
        return coordinates_lat_in;
    }

    public void setCoordinates_lat_in(double coordinates_lat_in) {
        this.coordinates_lat_in = coordinates_lat_in;
    }

    public double getCoordinates_lat_out() {
        return coordinates_lat_out;
    }

    public void setCoordinates_lat_out(double coordinates_lat_out) {
        this.coordinates_lat_out = coordinates_lat_out;
    }

    public double getCoordinates_lon_in() {
        return coordinates_lon_in;
    }

    public void setCoordinates_lon_in(double coordinates_lon_in) {
        this.coordinates_lon_in = coordinates_lon_in;
    }

    public double getCoordinates_lon_out() {
        return coordinates_lon_out;
    }

    public void setCoordinates_lon_out(double coordinates_lon_out) {
        this.coordinates_lon_out = coordinates_lon_out;
    }

    public String getDate_planned() {
        return date_planned;
    }

    public void setDate_planned(String date_planned) {
        this.date_planned = date_planned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(int requirement_id) {
        this.requirement_id = requirement_id;
    }

    public String getRequirement_reason() {
        return requirement_reason;
    }

    public void setRequirement_reason(String requirement_reason) {
        this.requirement_reason = requirement_reason;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_ambassador_in() {
        return school_ambassador_in;
    }

    public void setSchool_ambassador_in(String school_ambassador_in) {
        this.school_ambassador_in = school_ambassador_in;
    }

    public String getSchool_amie() {
        return school_amie;
    }

    public void setSchool_amie(String school_amie) {
        this.school_amie = school_amie;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_parish() {
        return school_parish;
    }

    public void setSchool_parish(String school_parish) {
        this.school_parish = school_parish;
    }

    public String getSchool_reference() {
        return school_reference;
    }

    public void setSchool_reference(String school_reference) {
        this.school_reference = school_reference;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Visita() {
    }

    public Visita (Parcel in){
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {

        check_in = in.readString();
        coordinates_lat_in = in.readDouble();
        coordinates_lat_out = in.readDouble();
        coordinates_lon_in = in.readDouble();
        coordinates_lon_out = in.readDouble();
        check_out = in.readString();
        date_planned = in.readString();
        id = in.readInt();
        observation = in.readString();
        requirement_id = in.readInt();
        requirement_reason = in.readString();
        school_id = in.readInt();
        school_address = in.readString();
        school_ambassador_in = in.readString();
        school_amie = in.readString();
        school_name = in.readString();
        school_parish = in.readString();
        school_reference = in.readString();
        state = in.readInt();
        type = in.readInt();
        user_id = in.readInt();
        user_type = in.readInt();
        username = in.readString();
        school_workday = in.readString();
        user_first_name = in.readString();
        user_last_name = in.readString();







    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

                parcel.writeString(check_in);
        parcel.writeDouble(coordinates_lat_in);
        parcel.writeDouble(coordinates_lat_out);
        parcel.writeDouble(coordinates_lon_in);
        parcel.writeDouble(coordinates_lon_out);
        parcel.writeString(check_out);
        parcel.writeString(date_planned);
        parcel.writeInt(id);
        parcel.writeString(observation);
        parcel.writeInt(requirement_id);
        parcel.writeString(requirement_reason);
        parcel.writeInt(school_id);
        parcel.writeString(school_address);
        parcel.writeString(school_ambassador_in);
        parcel.writeString(school_amie);
        parcel.writeString(school_name);
        parcel.writeString(school_parish);
        parcel.writeString(school_reference);
        parcel.writeInt(state);
        parcel.writeInt(type);
        parcel.writeInt(user_id);
        parcel.writeInt(user_type);
        parcel.writeString(username);
        parcel.writeString(school_workday);
        parcel.writeString(user_first_name);
        parcel.writeString(user_last_name);






    }

    @Override
    public int compareTo(Object o) {

        int estadoVisita2 = ((Visita) o).state;

        //ascendente
        return this.state-estadoVisita2;
    }
}
