package shared.network;

public interface SharedMiddleware {
    public String startGame(String uuid, boolean isSocket);
}