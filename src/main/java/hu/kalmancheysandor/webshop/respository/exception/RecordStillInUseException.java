package hu.kalmancheysandor.webshop.respository.exception;

public class RecordStillInUseException extends RuntimeException{
    private int id;

    public RecordStillInUseException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
