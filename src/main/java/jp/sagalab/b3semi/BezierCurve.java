package jp.sagalab.b3semi;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class BezierCurve {
  public static BezierCurve create(List<Point2D.Double> _controlPoints) {
    return new BezierCurve(_controlPoints);
  }

  /**
   * ベジェ曲線をパラメーター _t で評価する
   * @param _t
   * @return 評価点
   */
  public Point2D.Double evaluate(double _t) {
      temppoint.clear();
//    if(m_controlPoints.size() < 2){
//      return new Point2D.Double(m_controlPoints.get(0).getX(),m_controlPoints.get(0).getY());
//    }
    if(m_controlPoints.size() != 1){
      for (int i=0;i<m_controlPoints.size()-1;i++) {
        double x = (1 - _t) * m_controlPoints.get(i).getX() + _t * m_controlPoints.get(i + 1).getX();
        double y = (1 - _t) * m_controlPoints.get(i).getY() + _t * m_controlPoints.get(i + 1).getY();
        Point2D.Double p = new Point2D.Double(x, y);
        temppoint.add(p);
      }

      BezierCurve bezierCurve = BezierCurve.create(temppoint);
      Point2D.Double lastpoint = bezierCurve.evaluate(_t);

      return new Point2D.Double (lastpoint.getX(),lastpoint.getY());
    }
    return new Point2D.Double(m_controlPoints.get(0).getX(),m_controlPoints.get(0).getY());
  }

  /* ↓ここから必要な関数を書き足していく↓ */


  private BezierCurve(List<Point2D.Double> _controlPoints) {
    m_controlPoints = _controlPoints;
  }

  private List<Point2D.Double> m_controlPoints;
  private List<Point2D.Double> temppoint = new ArrayList<>();
}
