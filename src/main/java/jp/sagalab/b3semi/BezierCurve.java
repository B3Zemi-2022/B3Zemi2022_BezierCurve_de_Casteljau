package jp.sagalab.b3semi;

import java.awt.geom.Point2D;
import java.util.List;

public final class BezierCurve {
  public static BezierCurve create(List<Point2D.Double> _controlPoints) {
    return new BezierCurve(_controlPoints);
  }

  /**
   * ベジェ曲線をパラメーター _t で評価する
   *
   * @param _t
   * @return 評価点
   */
  public Point2D.Double evaluate(double _t) {
    double x1 = (1-_t)*m_controlPoints.get(0).getX()+_t*m_controlPoints.get(1).getX();
    double y1 = (1-_t)*m_controlPoints.get(0).getY()+_t*m_controlPoints.get(1).getY();
    double x2 = (1-_t)*m_controlPoints.get(1).getX()+_t*m_controlPoints.get(2).getX();
    double y2 = (1-_t)*m_controlPoints.get(1).getY()+_t*m_controlPoints.get(2).getY();
    double x3 = ((1 - _t) * x1) + _t * x2;
    double y3 = ((1 - _t) * y1) + _t * y2;
    return new Point2D.Double(x3, y3);
  }

  /* ↓ここから必要な関数を書き足していく↓ */

  /*public List<Double> naibun(double x_1, double x_2){
    for (double i=0;i<=1;i++){
      naibunlist.add((x_1)*(1-i)+(x_2)*i);
      return naibunlist;
    }
  };

   */

  private List<Double> naibunlist;


  private BezierCurve(List<Point2D.Double> _controlPoints) {
    m_controlPoints = _controlPoints;
  }

  private List<Point2D.Double> m_controlPoints;
}
