package Controller;

public interface FileConnection<T> {
  void open(boolean readMode);
  T create(String data);
  void close();
}
