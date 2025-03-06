package utilities;

public class Role {
    protected RoleType type;
    public Role(RoleType type){
        this.type = type;
    }

    public RoleType getType(){
        return type;
    }
}
