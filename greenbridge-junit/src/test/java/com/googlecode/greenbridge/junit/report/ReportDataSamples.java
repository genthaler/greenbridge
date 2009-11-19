/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import com.googlecode.greenbridge.annotation.Story2121;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class ReportDataSamples {

    public static Report loadDataset1() {
        Report r = new Report();
        r.setTotal_failing(0);
        r.setTotal_incomplete(5);
        r.setTotal_passing(2);
        List<StorySource> sources = new ArrayList<StorySource>();
        r.setStorySources(sources);
        {
            StorySource ss = new StorySource();
            sources.add(ss);
            ss.setSourceName("A-test-1.0");
            ss.setState(StorySource.SOURCE_STATE.INCOMPLETE);
            List<StoryReport> storyReports = new ArrayList<StoryReport>();
            ss.setStoryReports(storyReports);
            {
                StoryReport sr = new StoryReport();
                storyReports.add(sr);
                sr.setStoryRef(new Story2121());
                sr.setState(StoryReport.STORY_STATE.INCOMPLETE);
            }
        }



        return r;
    }
}
