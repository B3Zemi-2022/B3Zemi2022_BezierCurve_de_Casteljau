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
    return new Point2D.Double(/* x, y */);
  }

  /* ↓ここから必要な関数を書き足していく↓ */

  private BezierCurve(List<Point2D.Double> _controlPoints) {
    m_controlPoints = _controlPoints;
  }

  private List<Point2D.Double> m_controlPoints;
}
