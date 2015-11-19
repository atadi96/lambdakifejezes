package lambdakifejezes.Connection;

public interface ResponseCodeListener {
	public void onError();
	public void onSuccess();
	public void onInvalid();
}
