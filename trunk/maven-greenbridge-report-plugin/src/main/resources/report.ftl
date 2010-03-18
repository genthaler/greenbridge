<!DOCTYPE HTML PUBLIC "f-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <style type="text/css" media="screen">
            @import url("scripts/style.css");
            @import url("scripts/jquery.autocomplete.css");
        </style>
        <script type="text/javascript" src="scripts/jquery.min.js "></script>
        <script type="text/javascript" src="scripts/jquery.parsequery.js" ></script>
        <script type="text/javascript" src="scripts/jquery-jtemplates.js"></script>
        <script type="text/javascript" src="scripts/jquery.corner.js"></script>
        <script type="text/javascript" src="scripts/jquery.autocomplete.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $('.story,.scenario').corner();
                $('.storyCount li').corner();
                $('.scenarioCount li').corner();
                $('#_view,#_filter').change(filterStories);
                $('#searchForm').bind('submit', function() {
                   showOnlyStoryOrScenarioById($('#tagsearch').val());
                   return false;
                });

                var id = findReferenceAnchor();
                if (id != null) {
                    showOnlyStoryOrScenarioById(id);
                }
                $('#tagsearch').autocomplete(buildAutoComplete()).result(function(event, data, formatted){
                   showOnlyStoryOrScenarioById(data);
                });
                $('.errorExpand').click(function() {
                    $(this).next('.errorTrace').toggle();
                });
            })

            function buildAutoComplete() {
                var list = new Array();
                $('.story, .scenario').each(function() {
                    list.push($(this).attr("id"));
                });
                return list;
            }


            function filterStories() {
                var view = $('#_view').val();
                var filter = $('#_filter').val();
                // do the view first
                doView(view);
                doFilter(filter);
            }

            function doFilter(filter) {
                if (filter == "") {
                    $('.story').show();
                } else {
                    var selector = '.story.' + filter;
                    $('.story').hide();
                    $(selector).show();
                }
            }



            function doView(view) {
                if (view == 'sc') {
                    $('.story').show();
                    $('.storyDescription, .scenario').hide();
                }
                if (view == 'scc') {
                    $('.story,.storyDescription,.scenario').show();
                    $('.scenarioDescription,.scenarioTable').hide();
                }
                if (view == 'st') {
                    $('.story,.storyDescription,.scenario,.scenarioDescription').show();
                    $('.scenarioTable').hide();
                }
                if (view == 'se') {
                    $('.story,.storyDescription,.scenario,.scenarioDescription,.scenarioTable').show();
                }
            }

            function showOnlyStoryOrScenarioById(id) {
                var ref = $('#' + id);
                if (ref.size() == 1) {
                    if (ref.hasClass('story')) {
                        $('.story').hide();
                        ref.show();
                    }
                    if (ref.hasClass('scenario')) {
                        $('.story').hide();
                        ref.parents('.story').show();
                    }
                    //ref.effect("highlight", {}, 3000);
                }
            }
            function findReferenceAnchor() {
                    var match = /html\?(\w+)/.exec(window.location);
                    if (match == null) return null;
                    return match[1];
            }

        </script>
    </head>
    <body>
        <div id="header">
            <img id="main_menu_img" src="images/logo.png" />
            <div id="main_menu">
                <div id="main_menu_title">${module.name} - Greenbridge Report</div>
                <div id="menu" >
                    View:
                    <select id="_view">
                        <option value="sc">Story Collapsed</option>
                        <option value="scc">Scenario Collapsed</option>
                        <option value="st">Scenario Text</option>
                        <option value="se">Scenario Expanded</option>
                    </select>
                    Filter:
                    <select id="_filter">
                        <option value="">None</option>
                        <option value="failed">Failed</option>
                        <option value="passed">Passed</option>
                        <option value="pending">Pending</option>
                    </select>

                    <div id="search">
                        <form id="searchForm">
                            <input type="text" id="tagsearch" name="tag" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <#list module.storyReports as story>
            <div class="story ${story.state}" id="${story.id}">
                <div class="storyName">
                    ${story.id}
                    <a href="${story.wikiLink}"><img src="images/external.png" border="0" /></a>
                </div>
                <ul class="storyCount">
                    <#if story.total_failing = 0>
                        <li class="fail-zero">&nbsp;</li>
                    </#if>
                    <#if (story.total_failing > 0)>
                        <li class="fail">${story.total_failing}</li>
                    </#if>
                    <#if story.total_pending = 0>
                        <li class="pend-zero">&nbsp;</li>
                    </#if>
                    <#if (story.total_pending > 0)>
                        <li class="pend">${story.total_pending}</li>
                    </#if>
                    <#if story.total_passing = 0>
                        <li class="pass-zero">&nbsp;</li>
                    </#if>
                    <#if (story.total_passing > 0)>
                        <li class="pass">${story.total_passing}</li>
                    </#if>
                </ul>
                <div class="storyDescription">
                    ${story.description}
                </div>
                <#list story.scenarioReports as scenario>
                    <div class="scenario ${scenario.state}" id="${scenario.id}">
                        <div class="scenarioName">
                            ${scenario.id}
                            <a href="${scenario.wikiLink}"><img src="images/external.png"  border="0" /></a>
                        </div>
                        <ul class="scenarioCount">
                            <#if scenario.total_failing = 0>
                                <li class="fail-zero">&nbsp;</li>
                            </#if>
                            <#if (scenario.total_failing > 0)>
                                <li class="fail">${scenario.total_failing}</li>
                            </#if>
                            <#if scenario.total_pending = 0>
                                <li class="pend-zero">&nbsp;</li>
                            </#if>
                            <#if (scenario.total_pending > 0)>
                                <li class="pend">${scenario.total_pending}</li>
                            </#if>
                            <#if scenario.total_passing = 0>
                                <li class="pass-zero">&nbsp;</li>
                            </#if>
                            <#if (scenario.total_passing > 0)>
                                <li class="pass">${scenario.total_passing}</li>
                            </#if>
                        </ul>
                        <div class="scenarioDescription">
                            ${scenario.description}
                        </div>
                        <table class="scenarioTable">
                            <#list scenario.results as result>
                                <tr>
                                    <td><a href="${result.clazzHref}">${result.clazz}</a></td>
                                    <td><a href="${result.clazzHref}">${result.method}</a></td>
                                    <td>${result.state}</td>
                                </tr>
                                <#if result.state = "failed">
                                    <tr>
                                      <td colspan='3'>
                                         <div class="errorMessage">
                                             <#if result.errorTrace??>
                                                <a href="#" class="errorExpand">+</a>
                                             </#if>
                                             ${result.errorMessage}
                                             <#if result.errorTrace??>
                                                 <div class="errorTrace">
                                                    ${result.errorTrace}
                                                 </div>
                                             </#if>
                                         </div>
                                      </td>
                                    </tr>
                                </#if>
                            </#list>
                        </table>

                    </div>
                </#list>
            </div>
        </#list>
        <div class="footer">
            Report Generated: ${generationDate?datetime?string.medium} by <a href="http://code.google.com/p/greenbridge/">Greenbridge</a>
            <div class="properties">
                <a href="#" id="prop-expand">+</a> System Properties
                <script type="text/javascript">
                        $(function() {
                                $('#prop-expand').click(function() {
                                        $('#properties-table').toggle();
                                });
                        });
                </script>
                <table id="properties-table">
                   <tr><th>Property</th><th>Value</th></tr>
                <#assign keys = properties?keys>
                <#list keys as key>
                    <tr><td>${key}</td><td>${properties[key]}</td></tr>
                </#list>
                </table>
            </div>
        </div>
    </body>
</html>