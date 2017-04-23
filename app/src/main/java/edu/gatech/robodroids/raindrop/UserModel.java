package edu.gatech.robodroids.raindrop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created By: RoboDroids
 */
public class UserModel implements Parcelable {
    private String name;
    private String userid;
    private String pass;
    private String type;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class).
     */
    public UserModel() {

    }

    /**
     * 4 argument constructor for a user object.
     * @param name the name of the user
     * @param userid the id (email) of the user
     * @param pass the password for the account
     * @param type the permission type of user
     */
    public UserModel(String name, String userid, String pass, String type) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = type;
    }

    /**
     * 3 argument constructor for a user object, no specified permission level.
     * @param name the name of the user
     * @param userid the id (email) of the user
     * @param pass the password for the account
     */
    public UserModel(String name, String userid, String pass) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = "User";
    }

    /**
     * Creator needed to regenerate a user object.
     * @return CREATOR
     */
    public static Creator<UserModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName());
        out.writeString(getUserid());
        out.writeString(getPass());
        out.writeString(getType());
    }

    /**
     * Constructor of user from a parcel.
     * @param in the parcel that the user is being constructed from.
     */
    private UserModel(Parcel in) {
        name = in.readString();
        userid = in.readString();
        pass = in.readString();
        type = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR =
                                        new Parcelable.Creator<UserModel>(){
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }
        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    /**
     *
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return String userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @param userid userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     *
     * @return string password
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *
     * @return String of the type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
