package model.utilities;

//a szerepek osztálya

public class Role {
    protected RoleType type;
    public Role(RoleType type){
        this.type = type;
    }

    public RoleType getType(){
        return type;
    }

    public String toString(){
        return switch (this.type){
            case RoleType.SHERIFF -> "Sheriff";
            case RoleType.OUTLAW -> "Outlaw";
            case RoleType.RENEGADE -> "Renegade";
            case RoleType.DEPUTY -> "Deputy";
        };
    }
}
