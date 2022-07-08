package jp.sagalab.b3semi;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yako, takashima
 */
public class Main extends JFrame {
  public Main() {
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
    addWindowListener(new WindowClosing());
    setState(JFrame.ICONIFIED);
    setIconImage(new ImageIcon("icon.jpg").getImage());
    m_canvas.setSize(800, 600);
    m_canvas.setBackground(Color.WHITE);
    setTitle("b3zemi");
    add(m_canvas);
    pack();
    setVisible( true );
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    m_canvas.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (m_controlPoints.size() < MAX_CONTROL_POINTS) {
            Point2D.Double p = new Point2D.Double(e.getX(), e.getY());

            // 打った点をリストに追加する
            m_controlPoints.add(p);

            // 打った点を描画する
            drawPoint(p.getX(), p.getY(),Color.BLACK,false);

            //打った点を結んだ線を描画する
            for(int i = 0; i<m_controlPoints.size()-1; ++i)
              drawLine(m_controlPoints.get(i),m_controlPoints.get(i+1),Color.lightGray);

            if (m_controlPoints.size() == MAX_CONTROL_POINTS) {
              drawflag = true;
            }
          }
          if(m_controlPoints.size() == MAX_CONTROL_POINTS && !drawflag) drawflag = true;
        }
      }
    );
    service.scheduleAtFixedRate(() -> {
      if(drawflag) {
        calcBezierCurve(drawcount);
        drawcount ++;
        if(drawcount > T){
          drawflag = false;
          drawcount = 0;
        }
      }
    }, 0, 100, TimeUnit.MILLISECONDS);
  }


  public void calcBezierCurve(double i){
    canvasClean();
    for(int j = 0; j<m_controlPoints.size()-1; ++j) drawLine(m_controlPoints.get(j),m_controlPoints.get(j+1),Color.lightGray);
    for (Point2D.Double m_controlPoint : m_controlPoints) drawPoint(m_controlPoint.getX(), m_controlPoint.getY(), Color.lightGray, false);
    BezierCurve bezierCurve = BezierCurve.create(m_controlPoints,m_canvas);
    /* ↓ここから必要な処理を書き足していく↓ */
    // コツ: bezierCurve.evaluate(_t) と drawLine(_p1, _p2) を駆使する
    Point2D.Double p = bezierCurve.evaluate(i/T);
    BezierPoints.add(p);
    if(i>0) for(int j=1; j<=i;++j)
      drawLine(BezierPoints.get(j-1),BezierPoints.get(j),Color.red);
    drawPoint(BezierPoints.get((int)i).getX(),BezierPoints.get((int)i).getY(),Color.red,true);
  }

  public void canvasClean(){
    Graphics g = (Graphics)m_canvas.getGraphics();
    g.setColor(Color.white);
    g.fillRect(0,0,800,600);
  }
  /**
   * 点を描画する
   * @param _x x座標
   * @param _y y座標
   */
  public void drawPoint(double _x, double _y, Color c, boolean fill) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    double radius = 6;
    g.setColor(c);
    Ellipse2D.Double oval = new Ellipse2D.Double(_x - radius/2, _y - radius/2, radius, radius);
    if(!fill) g.fill(oval);
    g.draw(oval);
  }

  /**
   * 線を描画する
   * @param _p1 始点
   * @param _p2 終点
   */
  public void drawLine(Point2D.Double _p1, Point2D.Double _p2, Color c) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    g.setColor(c);
    Stroke BStroke = new BasicStroke(2.0f);
    g.setStroke(BStroke);
    Line2D.Double line = new Line2D.Double(_p1, _p2);
    g.draw(line);
  }

  /**
   * @param _args the command line arguments
   */
  public static void main(String[] _args){ new Main();}

  /** キャンバスを表す変数 */
  private Canvas m_canvas = new Canvas();

  /** クリックで打たれた点を保持するリスト */
  private List<Point2D.Double> m_controlPoints = new ArrayList<>();

  /** 求めたベジェ曲線の点を保持するリスト */
  private List<Point2D.Double> BezierPoints = new ArrayList<>();

  /** 点の数の上限
   * （[MAX_CONTROL_POINTS]個の点を打つと、[MAX_CONTROL_POINTS - 1]次のベジェ曲線が描かれる）
   */
  private static final int MAX_CONTROL_POINTS = 5;
  private static final int T = 100;

  private static boolean drawflag = false;

  private static double drawcount = 0;

  /**
   * ウィンドウを閉じる時の確認ダイアログを表すクラス
   */
  class WindowClosing extends WindowAdapter {
    public void windowClosing(WindowEvent _e){
      int ans = JOptionPane.showConfirmDialog(Main.this, "Are you sure you want to finish?");
      if(ans == JOptionPane.YES_OPTION){
        System.exit(0);
      }
    }
  }
}

