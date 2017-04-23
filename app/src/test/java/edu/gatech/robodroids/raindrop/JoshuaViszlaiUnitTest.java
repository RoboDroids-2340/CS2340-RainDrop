package edu.gatech.robodroids.raindrop;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by jviszlai on 4/9/17.
 */
public class JoshuaViszlaiUnitTest {

    @Test
    public void validInputTest() {
        QualityReportModel testReport =
                ValidateQualityInput.inputToQualityReport(
                        "3.3", "3.2", "Treatable",
                "100.1", "99.9", "Josh", "10371932");
        Assert.assertNotNull(testReport);
        Assert.assertEquals(testReport.getLat(), 3.3);
        Assert.assertEquals(testReport.getLon(), 3.2);
        Assert.assertEquals(testReport.getWaterCondition(), "Treatable");
        Assert.assertEquals(testReport.getVirusPPM(), 100.1);
        Assert.assertEquals(testReport.getContaminantPPM(), 99.9);
        Assert.assertEquals(testReport.getReporterName(), "Josh");
        Assert.assertEquals(testReport.getSubmissionTime(), "10371932");
    }

    @Test
    public void nullInputTest() {
        QualityReportModel testReport =
                ValidateQualityInput.inputToQualityReport(
                        "3.3", "3.2", null,
                        "100,1", "99.9", "Josh", "10371932");
        Assert.assertEquals(testReport, null);
    }

    @Test
    public void nonNumericInputTest() {
        QualityReportModel testReport1 =
                ValidateQualityInput.inputToQualityReport(
                        "notNum", "3.2", "Treatable",
                        "100.1", "99.9", "Josh", "10371932");
        QualityReportModel testReport2 =
                ValidateQualityInput.inputToQualityReport(
                        "3.3", "notNum", "Treatable",
                        "100.1", "99.9", "Josh", "10371932");
        QualityReportModel testReport3 =
                ValidateQualityInput.inputToQualityReport(
                        "3.3", "3.2", "Treatable",
                        "notNum", "99.9", "Josh", "10371932");
        QualityReportModel testReport4 =
                ValidateQualityInput.inputToQualityReport(
                        "3.3", "3.2", "Treatable",
                        "100.1", "notNum", "Josh", "10371932");
        Assert.assertEquals(testReport1, null);
        Assert.assertEquals(testReport2, null);
        Assert.assertEquals(testReport3, null);
        Assert.assertEquals(testReport4, null);

    }

}
