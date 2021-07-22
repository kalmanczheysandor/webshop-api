package hu.kalmancheysandor.webshop.respository.exception;

public class RecordNotFoundByIdException extends RuntimeException{
    private Integer id;

    public RecordNotFoundByIdException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
