import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureCook {

  public static void main(String[] args)  {
    long startTime = System.currentTimeMillis();
    Callable<Chuju> onlineShopping = () -> {
      System.out.println("第一步：下单");
      System.out.println("第一步：等待送货");
      Thread.sleep(5000);  // 模拟送货时间
      System.out.println("第一步：快递送到");
      Chuju r = new Chuju();
      r.a = 2;
      return r;
    };
    FutureTask<Chuju> task = new FutureTask<>(onlineShopping);
    new Thread(task).start();

    Callable<Chuju> onlineShopping1 = () -> {
      System.out.println("第一步：下单");
      System.out.println("第一步：等待送货");
      Thread.sleep(5000);  // 模拟送货时间
      System.out.println("第一步：快递送到");
      Chuju r = new Chuju();
      r.a = 3;
      return r;
    };
    FutureTask<Chuju> task1 = new FutureTask<>(onlineShopping1);
    new Thread(task1).start();

    List<FutureTask> list = new ArrayList<>();
    list.add(task);
    list.add(task1);

    new Thread(() -> {
      try {

        while (true) {

          boolean isNotOver = false;
          for (FutureTask t : list) {
            if (!t.isDone()) {
              isNotOver = true;
            }
          }
          if (isNotOver) {
            Thread.sleep(1000);
          } else {
            break;
          }
        }
        Chuju chuju = task.get();
        Chuju chuju1 = task1.get();
        System.out.println("task=" + chuju.a);
        System.out.println("task1=" + chuju1.a);
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }).start();

    System.out.println("main over" + (System.currentTimeMillis() - startTime) + "ms");
  }

  // 厨具类
  static class Chuju {
    public int a = 1;
  }


}