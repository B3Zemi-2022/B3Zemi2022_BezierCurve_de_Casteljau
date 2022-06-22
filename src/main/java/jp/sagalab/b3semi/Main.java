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

    m_canvas.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (m_controlPoints.size() < MAX_CONTROL_POINTS) {
            Point2D.Double p = new Point2D.Double(e.getX(), e.getY());

            // 打った点をリストに追加する
            m_controlPoints.add(p);
            // 打った点を描画する
            drawPoint(p.getX(), p.getY());

            // 点が揃ったら
            if (m_controlPoints.size() == MAX_CONTROL_POINTS) {
              // ベジェ曲線を描画
              drawBezierCurve();
            }
          }
        }
      }
    );
  }

  public void drawBezierCurve() {
    BezierCurve bezierCurve = BezierCurve.create(m_controlPoints);

    /* ↓ここから必要な処理を書き足していく↓ */
    // コツ: bezierCurve.evaluate(_t) と drawLine(_p1, _p2) を駆使する
  }

  /**
   * 点を描画する
   * @param _x x座標
   * @param _y y座標
   */
  public void drawPoint(double _x, double _y) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    double radius = 6;
    Ellipse2D.Double oval = new Ellipse2D.Double(_x, _y, radius, radius);
    g.draw(oval);
  }

  /**
   * 線を描画する
   * @param _p1 始点
   * @param _p2 終点
   */
  public void drawLine(Point2D.Double _p1, Point2D.Double _p2) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    Line2D.Double line = new Line2D.Double(_p1, _p2);
    g.draw(line);
  }

  /**
   * @param _args the command line arguments
   */
  public static void main(String[] _args){ new Main(); }

  /** キャンバスを表す変数 */
  private Canvas m_canvas = new Canvas();

  /** クリックで打たれた点を保持するリスト */
  private List<Point2D.Double> m_controlPoints = new ArrayList<>();

  /** 点の数の上限
   * （[MAX_CONTROL_POINTS]個の点を打つと、[MAX_CONTROL_POINTS - 1]次のベジェ曲線が描かれる）
   */
  private static final int MAX_CONTROL_POINTS = 3;

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

