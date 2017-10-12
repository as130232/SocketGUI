package command;

public enum Module {
	LOADING,
	MAG,
	OP1,
	OP2,
	OP3,
	AREAA,
	AREAB,
	AREAC;
	
	public static Module convertByString(String aplCommandTypeId){
		return Module.valueOf(aplCommandTypeId);
	}
}
