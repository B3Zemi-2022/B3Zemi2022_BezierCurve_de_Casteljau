package jp.sagalab.b3semi;

import java.awt.geom.Point2D;
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
    double x;
    double y;
    double p_x,p_y;
    double q_x,q_y;


      p_x = m_controlPoints.get(0).x * (1 - _t) + m_controlPoints.get(1).x * _t;
      p_y = m_controlPoints.get(0).y * (1 - _t) + m_controlPoints.get(1).y * _t;
      q_x = m_controlPoints.get(1).x * (1 - _t) + m_controlPoints.get(2).x * _t;
      q_y = m_controlPoints.get(1).y * (1 - _t) + m_controlPoints.get(2).y * _t;

    x = p_x*(1- _t) + q_x*_t;
    y = p_y*(1- _t) + q_y*_t;
    return new Point2D.Double(x, y);
  }

  /* ↓ここから必要な関数を書き足していく↓ */

  private BezierCurve(List<Point2D.Double> _controlPoints) {
    m_controlPoints = _controlPoints;
  }

  private List<Point2D.Double> m_controlPoints;
}
