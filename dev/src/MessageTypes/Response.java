package MessageTypes;

public class Response<T> {

    private Exception _exception;
    public T returnValue;

    public Response(Exception _exception, T returnValue) {
        this._exception = _exception;
        this.returnValue = returnValue;
    }
    public Response(Exception _exception) {
        this._exception = _exception;
    }

    public Exception get_exception() {
        return _exception;
    }

    public void set_exception(Exception _exception) {
        this._exception = _exception;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }
}
