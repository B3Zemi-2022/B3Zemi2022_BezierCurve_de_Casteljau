package jp.sagalab.b3semi;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Line2D;

public final class BezierCurve {
  public static BezierCurve create(List<Point2D.Double> _controlPoints, Canvas _canvas) {
    return new BezierCurve(_controlPoints, _canvas);
  }

  /**
   * ベジェ曲線をパラメーター _t で評価する
   * @param _t
   * @return 評価点
   */
  public Point2D.Double evaluate(double _t) {
    tempPoints.clear();
    if(m_controlPoints.size() != 1) {
      for (int i = 0; i < m_controlPoints.size() - 1; ++i) {
        tempPoints.add(internal(i,_t));
      }
      if(tempPoints.size() > 1) drawControl();
      BezierCurve bezierCurve = BezierCurve.create(tempPoints,m_canvas);
      Point2D.Double points = bezierCurve.evaluate(_t);
      return new Point2D.Double(points.getX(), points.getY());
    }
    return new Point2D.Double(m_controlPoints.get(0).getX(),m_controlPoints.get(0).getY());
  }

  /* ↓ここから必要な関数を書き足していく↓ */

  public Point2D.Double internal(int point, double _t){
    double x = (1 - _t) * m_controlPoints.get(point).getX() + _t * m_controlPoints.get(point + 1).getX();
    double y = (1 - _t) * m_controlPoints.get(point).getY() + _t * m_controlPoints.get(point + 1).getY();
    Point2D.Double p = new Point2D.Double(x, y);
    return p;
  }

  public void drawControl(){
    for (Point2D.Double tempPoint : tempPoints)
      drawPoint(tempPoint.getX(), tempPoint.getY(), Color.green);
    for(int i=0; i <tempPoints.size()-1;++i)
      drawLine(tempPoints.get(i),tempPoints.get(i+1),Color.green);
  }

  public void drawPoint(double _x, double _y, Color c) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    double radius = 6;
    g.setColor(c);
    Ellipse2D.Double oval = new Ellipse2D.Double(_x - radius/2, _y - radius/2, radius, radius);
    g.fill(oval);
    g.draw(oval);
  }

  public void drawLine(Point2D.Double _p1, Point2D.Double _p2, Color c) {
    Graphics2D g = (Graphics2D)m_canvas.getGraphics();
    g.setColor(c);
    Stroke BStroke = new BasicStroke(2.0f);
    g.setStroke(BStroke);
    Line2D.Double line = new Line2D.Double(_p1, _p2);
    g.draw(line);
  }

  private BezierCurve(List<Point2D.Double> _controlPoints, Canvas _canvas) {
    m_controlPoints = _controlPoints;
    m_canvas = _canvas;
  }

  private List<Point2D.Double> m_controlPoints;

  private Canvas m_canvas;
  List<Point2D.Double> tempPoints = new ArrayList<>();
}
