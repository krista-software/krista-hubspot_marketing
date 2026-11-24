/*
 * Hubspot Marketing Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.krista.extension.crm.marketing.hubspot_marketing;

public class TestConstants {
    public static final String ACCESS_TOKEN = "{"
            + "\"access_token\": \"sampleAccessToken\","
            + "\"expires_in\": \"invalidValue\","
            + "\"token_type\": \"Bearer\","
            + "\"refresh_token\": \"sampleRefreshToken\""
            + "}";


    public static final String CREATE_EMAIL = "{\n" +
            "    \"ab\": false,\n" +
            "    \"abHoursToWait\": 4,\n" +
            "    \"abSampleSizeDefault\": null,\n" +
            "    \"abSamplingDefault\": null,\n" +
            "    \"abSuccessMetric\": null,\n" +
            "    \"abTestPercentage\": 50,\n" +
            "    \"abVariation\": false,\n" +
            "    \"absoluteUrl\": \"http://44087749.hs-sites.com/-temporary-slug-c4076ad9-1384-4eb4-9523-4b29716d4614\",\n" +
            "    \"aifeatures\": null,\n" +
            "    \"allEmailCampaignIds\": [],\n" +
            "    \"analyticsPageId\": \"151204725377\",\n" +
            "    \"analyticsPageType\": \"email\",\n" +
            "    \"archivedAt\": 0,\n" +
            "    \"archivedInDashboard\": false,\n" +
            "    \"audienceAccess\": \"PUBLIC\",\n" +
            "    \"blogRssSettings\": null,\n" +
            "    \"canSpamSettingsId\": 139489770387,\n" +
            "    \"categoryId\": 2,\n" +
            "    \"contentAccessRuleIds\": [],\n" +
            "    \"contentAccessRuleTypes\": [],\n" +
            "    \"contentTypeCategory\": 2,\n" +
            "    \"createPage\": false,\n" +
            "    \"created\": 1703582207471,\n" +
            "    \"createdById\": 61028542,\n" +
            "    \"currentState\": \"DRAFT\",\n" +
            "    \"currentlyPublished\": false,\n" +
            "    \"customReplyTo\": \"\",\n" +
            "    \"customReplyToEnabled\": false,\n" +
            "    \"domain\": \"\",\n" +
            "    \"emailBody\": \"{% content_attribute \\\"email_body\\\" %}{{ default_email_body }}{% end_content_attribute %}\",\n" +
            "    \"emailNote\": \"\",\n" +
            "    \"emailTemplateMode\": \"DRAG_AND_DROP\",\n" +
            "    \"emailType\": \"BATCH_EMAIL\",\n" +
            "    \"feedbackSurveyId\": null,\n" +
            "    \"flexAreas\": {},\n" +
            "    \"freezeDate\": 1703582207471,\n" +
            "    \"fromName\": \"Aradhya Gudadhe\",\n" +
            "    \"hasContentAccessRules\": false,\n" +
            "    \"htmlTitle\": \"\",\n" +
            "    \"id\": 151204725377,\n" +
            "    \"isCreatedFomSandboxSync\": false,\n" +
            "    \"isGraymailSuppressionEnabled\": true,\n" +
            "    \"isInstanceLayoutPage\": false,\n" +
            "    \"isPublished\": false,\n" +
            "    \"isRecipientFatigueSuppressionEnabled\": null,\n" +
            "    \"language\": \"en\",\n" +
            "    \"layoutSections\": {},\n" +
            "    \"liveDomain\": \"44087749.hs-sites.com\",\n" +
            "    \"mailingListsExcluded\": [],\n" +
            "    \"mailingListsIncluded\": [],\n" +
            "    \"maxRssEntries\": 5,\n" +
            "    \"metaDescription\": \"\",\n" +
            "    \"name\": \"mmmmmm\",\n" +
            "    \"pageExpiryEnabled\": false,\n" +
            "    \"pageRedirected\": false,\n" +
            "    \"pastMabExperimentIds\": [],\n" +
            "    \"portalId\": 44087749,\n" +
            "    \"previewKey\": \"bwRrNKKs\",\n" +
            "    \"processingStatus\": \"\",\n" +
            "    \"publishDate\": 1703582207697,\n" +
            "    \"publishImmediately\": true,\n" +
            "    \"publishedUrl\": \"\",\n" +
            "    \"replyTo\": \"9665830211a@gmail.com\",\n" +
            "    \"resolvedDomain\": \"44087749.hs-sites.com\",\n" +
            "    \"rssEmailByText\": \"By\",\n" +
            "    \"rssEmailClickThroughText\": \"Read more &raquo;\",\n" +
            "    \"rssEmailCommentText\": \"Comment &raquo;\",\n" +
            "    \"rssEmailEntryTemplateEnabled\": false,\n" +
            "    \"rssEmailImageMaxWidth\": 0,\n" +
            "    \"rssEmailUrl\": \"\",\n" +
            "    \"sections\": {},\n" +
            "    \"securityState\": \"NONE\",\n" +
            "    \"slug\": \"-temporary-slug-c4076ad9-1384-4eb4-9523-4b29716d4614\",\n" +
            "    \"smartEmailFields\": null,\n" +
            "    \"state\": \"DRAFT\",\n" +
            "    \"styleSettings\": null,\n" +
            "    \"subcategory\": \"batch\",\n" +
            "    \"subject\": \"created from api main\",\n" +
            "    \"subscription\": 275386342,\n" +
            "    \"subscriptionName\": \"Custom Subscription\",\n" +
            "    \"teamPerms\": [],\n" +
            "    \"templatePath\": \"EMAIL_DND_TEMPLATE/test template 1.html\",\n" +
            "    \"transactional\": false,\n" +
            "    \"translations\": {},\n" +
            "    \"unpublishedAt\": 0,\n" +
            "    \"updated\": 1703582207471,\n" +
            "    \"updatedById\": 61028542,\n" +
            "    \"url\": \"http://44087749.hs-sites.com/-temporary-slug-c4076ad9-1384-4eb4-9523-4b29716d4614\",\n" +
            "    \"useRssHeadlineAsSubject\": false,\n" +
            "    \"userPerms\": [],\n" +
            "    \"vidsExcluded\": [],\n" +
            "    \"vidsIncluded\": [],\n" +
            "    \"widgets\": {}\n" +
            "}";
}