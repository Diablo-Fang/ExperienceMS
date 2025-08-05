package com.dkd.manage.utils;

import com.dkd.manage.domain.Experiment1;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeepSeek {
private static ExecutorService executor =  Executors.newFixedThreadPool(10);
    // API终端点和模型配置
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String MODEL_NAME = "deepseek-coder";
    private static String apiKey = "sk-6df4d625b0c643d5970d119e0019cf6d"; // 替换为实际API密钥

    public static void main(String[] args) {
        // 示例：学生完成实验的时间（单位：分钟）
        int completionTimeInMinutes = 100;

        try {
            // 利用DeepSeek大模型判断学生是否有作弊嫌疑
            String analysisResult = judgeCheatingSuspicionWithAI(completionTimeInMinutes);

            // 输出结果
            System.out.println("AI分析结果：");
            System.out.println(analysisResult);
        } catch (Exception e) {
            System.err.println("分析失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 利用DeepSeek大模型判断学生实验是否存在作弊嫌疑
     * @param completionTimeInMinutes 学生完成实验的时间（分钟）
     * @return 对于学生是否存在作弊嫌疑的判断文本
     */
    public static String judgeCheatingSuspicionWithAI(int completionTimeInMinutes) throws Exception{
        // 构建请求参数
        Map<String, String> params = new HashMap<>();
        params.put("completionTime", String.valueOf(completionTimeInMinutes));

        // 生成判断结果
        String result = analyzeCheatingPossibility(apiKey, params);

        // 提取判断文本
//        return (String) result.get("analysis");
        return result;
    }

    /**
     * 向DeepSeek API发送请求，分析作弊可能性
     * @param apiKey API密钥
     * @param parameters 包含完成时间的参数
     * @return API返回的结果
     */
    private static String analyzeCheatingPossibility(String apiKey, Map<String, String> parameters) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 创建请求体
            ObjectNode requestBody = mapper.createObjectNode();
            requestBody.put("model", MODEL_NAME);

            ArrayNode messages = mapper.createArrayNode();

            // 系统消息
            ObjectNode systemMessage = mapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一个专业的学术行为分析助手，根据用户提供的实验完成时间分析是否存在作弊可能。请始终以JSON格式响应。");
            messages.add(systemMessage);

            // 用户消息
            ObjectNode userMessage = mapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", buildCheatingAnalysisPrompt(parameters));
            messages.add(userMessage);

            requestBody.set("messages", messages);
            requestBody.put("temperature", 0.2);  // 降低温度以获得更确定性的结果
            requestBody.put("max_tokens", 4000);  // 增加token限制
            requestBody.put("response_format", mapper.createObjectNode().put("type", "json_object")); // 指定返回JSON格式

            // 使用 Apache HttpClient 发送请求
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(DEEPSEEK_API_URL);
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
                httpPost.setHeader("Authorization", "Bearer " + apiKey);
                httpPost.setHeader("Accept", "application/json; charset=UTF-8");

                // 使用UTF-8编码确保中文正确传输
                StringEntity entity = new StringEntity(requestBody.toString(), StandardCharsets.UTF_8);
                entity.setContentType("application/json; charset=UTF-8");
                httpPost.setEntity(entity);

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    HttpEntity responseEntity = response.getEntity();

                    if (responseEntity != null) {
                        // 确保使用UTF-8编码读取响应
                        String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

                        // 调试输出
                        System.out.println("API响应原始内容: " + responseString.substring(0, Math.min(200, responseString.length())) + "...");

                        JsonNode responseJson = mapper.readTree(responseString);

                        if (responseJson.has("error")) {
                            throw new RuntimeException("API错误: " + responseJson.get("error").toString());
                        }

                        String content = responseJson.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();

                        // 调试输出
                        System.out.println("API返回内容: " + content.substring(0, Math.min(200, content.length())) + "...");

                        String jsonPart = extractJson(content);
//                        // 直接将JSON字符串转换为Map，使用UTF-8编码
//                        Map<String, Object> result = mapper.readValue(jsonPart, Map.class);
                        return jsonPart;
                    } else {
                        throw new RuntimeException("API响应为空");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("分析作弊可能性失败: " + e.getMessage(), e);
        }
    }

    /**
     * 构造向DeepSeek发送的提示词
     * @param parameters 包含完成时间的参数
     * @return 提示词内容
     */
    private static String buildCheatingAnalysisPrompt(Map<String, String> parameters) {
        int completionTimeInMinutes = Integer.parseInt(parameters.getOrDefault("completionTime", "60"));

        return String.format(
                "请根据以下信息分析学生是否存在作弊嫌疑：\n\n" +
                        "1. 实验完成时间：%d 分钟\n\n" +
                        "分析要求：\n" +
                        "- 如果完成时间小于30分钟，判定为高概率作弊（95%%）\n" +
                        "- 如果完成时间大于90分钟，判定为低概率作弊（5%%）\n" +
                        "- 在30-90分钟范围内，作弊概率随时间增加线性下降\n\n" +
                        "严格以以下JSON格式输出，不包含任何其他内容：\n" +
                        "{\n" +
                        "  \"suspicious\": boolean,          // 是否存在作弊嫌疑\n" +
                        "  \"probability\": double,           // 作弊概率（0-100）\n" +
                        "  \"analysis\": \"分析说明\"           // 分析说明（中文）\n" +
                        "}\n\n" +
                        "示例：\n" +
                        "{\n" +
                        "  \"suspicious\": true,\n" +
                        "  \"probability\": 95.0,\n" +
                        "  \"analysis\": \"完成时间短于30分钟，存在高概率作弊嫌疑\"\n" +
                        "}",
                completionTimeInMinutes
        );
    }


    private static String extractJson(String content) {
        // 先尝试直接解析完整JSON
        if (content.trim().startsWith("{") && content.trim().endsWith("}")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.readTree(content);
                return content;
            } catch (Exception e) {
                // 无效JSON，尝试提取JSON部分
            }
        }

        // 使用正则表达式提取JSON内容
        Pattern pattern = Pattern.compile("\\{(?:[^{}]|(?s:\\{(?:[^{}]|(?s:\\{(?:[^{}]|)*\\})*)*\\}))*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String jsonPart = matcher.group(0);
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.readTree(jsonPart);
                return jsonPart;
            } catch (Exception e) {
                throw new IllegalArgumentException("提取的JSON内容仍然无效");
            }
        }

        throw new IllegalArgumentException("响应中未找到有效的JSON内容");
    }

    /**
     * 为实验报告生成评分
     * @param experimentData 学生实验数据
     * @param referenceData 标准参考数据
     * @return 包含评分和评语的JSON字符串
     */
    public static String generateExperimentScore(Experiment1 experimentData, Map<String, Object> referenceData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 构建请求体
            ObjectNode requestBody = mapper.createObjectNode();
            requestBody.put("model", MODEL_NAME);

            ArrayNode messages = mapper.createArrayNode();

            // 系统消息
            ObjectNode systemMessage = mapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一位资深的电子实验指导老师，请根据提供的实验数据和标准参考数据，从数据准确性、波形符合度和操作规范性三个维度对实验进行评分（满分100分），并给出简要评语。请始终以JSON格式响应。");
            messages.add(systemMessage);

            // 用户消息 - 构建详细的实验数据对比
            ObjectNode userMessage = mapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", buildScoreEvaluationPrompt(experimentData, referenceData));
            messages.add(userMessage);

            requestBody.set("messages", messages);
            requestBody.put("temperature", 0.2);
            requestBody.put("max_tokens", 4000);
            requestBody.put("response_format", mapper.createObjectNode().put("type", "json_object"));

            // 使用 Apache HttpClient 发送请求
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(DEEPSEEK_API_URL);
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
                httpPost.setHeader("Authorization", "Bearer " + apiKey);
                httpPost.setHeader("Accept", "application/json; charset=UTF-8");

                StringEntity entity = new StringEntity(requestBody.toString(), StandardCharsets.UTF_8);
                entity.setContentType("application/json; charset=UTF-8");
                httpPost.setEntity(entity);

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    HttpEntity responseEntity = response.getEntity();

                    if (responseEntity != null) {
                        String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                        JsonNode responseJson = mapper.readTree(responseString);

                        if (responseJson.has("error")) {
                            throw new RuntimeException("API错误: " + responseJson.get("error").toString());
                        }

                        String content = responseJson.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();

                        return extractJson(content);
                    } else {
                        throw new RuntimeException("API响应为空");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("实验评分失败: " + e.getMessage(), e);
        }
    }

    /**
     * 构造实验评分的提示词
     * @param experimentData 学生实验数据
     * @param referenceData 标准参考数据
     * @return 提示词内容
     */
    private static String buildScoreEvaluationPrompt(Experiment1 experimentData, Map<String, Object> referenceData) {
        // 构建学生数据JSON字符串
        StringBuilder studentDataBuilder = new StringBuilder();
        studentDataBuilder.append("{\n");
        appendField(studentDataBuilder, "table1vc1Field", experimentData.getTable1vc1Field(), referenceData.get("table1vc1Field"));
        appendField(studentDataBuilder, "table1vb1Field", experimentData.getTable1vb1Field(), referenceData.get("table1vb1Field"));
        appendField(studentDataBuilder, "table1ve1Field", experimentData.getTable1ve1Field(), referenceData.get("table1ve1Field"));
        appendField(studentDataBuilder, "table1vc2Field", experimentData.getTable1vc2Field(), referenceData.get("table1vc2Field"));
        appendField(studentDataBuilder, "table1vb2Field", experimentData.getTable1vb2Field(), referenceData.get("table1vb2Field"));
        appendField(studentDataBuilder, "table1ve2Field", experimentData.getTable1ve2Field(), referenceData.get("table1ve2Field"));
        appendField(studentDataBuilder, "table2vs1Field", experimentData.getTable2vs1Field(), referenceData.get("table2vs1Field"));
        appendField(studentDataBuilder, "table2vol1Field", experimentData.getTable2vol1Field(), referenceData.get("table2vol1Field"));
        appendField(studentDataBuilder, "table2avl1Field", experimentData.getTable2avl1Field(), referenceData.get("table2avl1Field"));
        appendField(studentDataBuilder, "table2vo1Field", experimentData.getTable2vo1Field(), referenceData.get("table2vo1Field"));
        appendField(studentDataBuilder, "table2av1Field", experimentData.getTable2av1Field(), referenceData.get("table2av1Field"));
        appendField(studentDataBuilder, "table2ri1Field", experimentData.getTable2ri1Field(), referenceData.get("table2ri1Field"));
        appendField(studentDataBuilder, "table2ro1Field", experimentData.getTable2ro1Field(), referenceData.get("table2ro1Field"));
        appendField(studentDataBuilder, "table2vo3Field", experimentData.getTable2vo3Field(), referenceData.get("table2vo3Field"));
        appendField(studentDataBuilder, "table2av3Field", experimentData.getTable2av3Field(), referenceData.get("table2av3Field"));
        appendField(studentDataBuilder, "table2w1Field", experimentData.getTable2w1Field(), referenceData.get("table2w1Field"));
        appendField(studentDataBuilder, "table2vs2Field", experimentData.getTable2vs2Field(), referenceData.get("table2vs2Field"));
        appendField(studentDataBuilder, "table2vol2Field", experimentData.getTable2vol2Field(), referenceData.get("table2vol2Field"));
        appendField(studentDataBuilder, "table2avl2Field", experimentData.getTable2avl2Field(), referenceData.get("table2avl2Field"));
        appendField(studentDataBuilder, "table2vo2Field", experimentData.getTable2vo2Field(), referenceData.get("table2vo2Field"));
        appendField(studentDataBuilder, "table2av2Field", experimentData.getTable2av2Field(), referenceData.get("table2av2Field"));
        appendField(studentDataBuilder, "table2ri2Field", experimentData.getTable2ri2Field(), referenceData.get("table2ri2Field"));
        appendField(studentDataBuilder, "table2ro2Field", experimentData.getTable2ro2Field(), referenceData.get("table2ro2Field"));
        appendField(studentDataBuilder, "table2vo4Field", experimentData.getTable2vo4Field(), referenceData.get("table2vo4Field"));
        appendField(studentDataBuilder, "table2av4Field", experimentData.getTable2av4Field(), referenceData.get("table2av4Field"));
        appendField(studentDataBuilder, "table2w2Field", experimentData.getTable2w2Field(), referenceData.get("table2w2Field"));
        appendField(studentDataBuilder, "table3vc11Field", experimentData.getTable3vc11Field(), referenceData.get("table3vc11Field"));
        appendField(studentDataBuilder, "table3vc21Field", experimentData.getTable3vc21Field(), referenceData.get("table3vc21Field"));
        appendField(studentDataBuilder, "table3vb11Field", experimentData.getTable3vb11Field(), referenceData.get("table3vb11Field"));
        appendField(studentDataBuilder, "table3vb21Field", experimentData.getTable3vb21Field(), referenceData.get("table3vb21Field"));
        appendField(studentDataBuilder, "table3ve11Field", experimentData.getTable3ve11Field(), referenceData.get("table3ve11Field"));
        appendField(studentDataBuilder, "table3ve21Field", experimentData.getTable3ve21Field(), referenceData.get("table3ve21Field"));
        appendField(studentDataBuilder, "table3vc12Field", experimentData.getTable3vc12Field(), referenceData.get("table3vc12Field"));
        appendField(studentDataBuilder, "table3vc22Field", experimentData.getTable3vc22Field(), referenceData.get("table3vc22Field"));
        appendField(studentDataBuilder, "table3vb12Field", experimentData.getTable3vb12Field(), referenceData.get("table3vb12Field"));
        appendField(studentDataBuilder, "table3vb22Field", experimentData.getTable3vb22Field(), referenceData.get("table3vb22Field"));
        appendField(studentDataBuilder, "table3ve12Field", experimentData.getTable3ve12Field(), referenceData.get("table3ve12Field"));
        appendField(studentDataBuilder, "table3ve22Field", experimentData.getTable3ve22Field(), referenceData.get("table3ve22Field"));
        appendField(studentDataBuilder, "table4vo11Field", experimentData.getTable4vo11Field(), referenceData.get("table4vo11Field"));
        appendField(studentDataBuilder, "table4vo21Field", experimentData.getTable4vo21Field(), referenceData.get("table4vo21Field"));
        appendField(studentDataBuilder, "table4vo1Field", experimentData.getTable4vo1Field(), referenceData.get("table4vo1Field"));
        appendField(studentDataBuilder, "table4a1Field", experimentData.getTable4a1Field(), referenceData.get("table4a1Field"));
        appendField(studentDataBuilder, "table4k1Field", experimentData.getTable4k1Field(), referenceData.get("table4k1Field"));
        appendField(studentDataBuilder, "table4vo12Field", experimentData.getTable4vo12Field(), referenceData.get("table4vo12Field"));
        appendField(studentDataBuilder, "table4vo22Field", experimentData.getTable4vo22Field(), referenceData.get("table4vo22Field"));
        appendField(studentDataBuilder, "table4vo2Field", experimentData.getTable4vo2Field(), referenceData.get("table4vo2Field"));
        appendField(studentDataBuilder, "table4a2Field", experimentData.getTable4a2Field(), referenceData.get("table4a2Field"));
        appendField(studentDataBuilder, "table4vo13Field", experimentData.getTable4vo13Field(), referenceData.get("table4vo13Field"));
        appendField(studentDataBuilder, "table4vo23Field", experimentData.getTable4vo23Field(), referenceData.get("table4vo23Field"));
        appendField(studentDataBuilder, "table4vo3Field", experimentData.getTable4vo3Field(), referenceData.get("table4vo3Field"));
        appendField(studentDataBuilder, "table4a3Field", experimentData.getTable4a3Field(), referenceData.get("table4a3Field"));
        appendField(studentDataBuilder, "table4k2Field", experimentData.getTable4k2Field(), referenceData.get("table4k2Field"));
        appendField(studentDataBuilder, "table4vo14Field", experimentData.getTable4vo14Field(), referenceData.get("table4vo14Field"));
        appendField(studentDataBuilder, "table4vo24Field", experimentData.getTable4vo24Field(), referenceData.get("table4vo24Field"));
        appendField(studentDataBuilder, "table4vo4Field", experimentData.getTable4vo4Field(), referenceData.get("table4vo4Field"));
        appendField(studentDataBuilder, "table4a4Field", experimentData.getTable4a4Field(), referenceData.get("table4a4Field"));
        appendField(studentDataBuilder, "table5vi1Field", experimentData.getTable5vi1Field(), referenceData.get("table5vi1Field"));
        appendField(studentDataBuilder, "table5vo1Field", experimentData.getTable5vo1Field(), referenceData.get("table5vo1Field"));
        appendField(studentDataBuilder, "table5vi2Field", experimentData.getTable5vi2Field(), referenceData.get("table5vi2Field"));
        appendField(studentDataBuilder, "table5vo2Field", experimentData.getTable5vo2Field(), referenceData.get("table5vo2Field"));
        appendField(studentDataBuilder, "currentText", experimentData.getCurrentText(), referenceData.get("currentText"));
        appendField(studentDataBuilder, "vi1vo1Image", experimentData.getVi1vo1Image(), "参考图像1");
        appendField(studentDataBuilder, "vi1vo2Image", experimentData.getVi1vo2Image(), "参考图像2");
        appendField(studentDataBuilder, "vo1vo2Image", experimentData.getVo1vo2Image(), "参考图像3");
        appendField(studentDataBuilder, "experimentScore", experimentData.getExperimentScore(), "参考评分");
        appendField(studentDataBuilder, "progress", experimentData.getProgress(), "参考进度");
        
        // 删除最后一个逗号和换行符
        if (studentDataBuilder.length() > 3) {
            studentDataBuilder.delete(studentDataBuilder.length() - 2, studentDataBuilder.length());
        }
        studentDataBuilder.append("\n}");

        return String.format(
                "请根据以下信息对学生实验进行评分：\n\n" +
                "学生数据：\n%s\n\n" +
                "标准参考数据：\n%s\n\n" +
                "评分要求：\n" +
                "1. 数据准确性：比较学生数据与标准数据的偏差\n" +
                "2. 波形符合度：分析波形图像与标准波形的匹配程度\n" +
                "3. 操作规范性：评估实验过程中的操作规范性\n" +
                "\n" +
                "严格以以下JSON格式输出，不包含任何其他内容：\n" +
                "{\n" +
                "  \"score\": double,           // 实验总分（0-100）\n" +
                "  \"accuracyScore\": double,     // 数据准确性得分\n" +
                "  \"waveScore\": double,        // 波形符合度得分\n" +
                "  \"operationScore\": double,    // 操作规范性得分\n" +
                "  \"evaluation\": \"分析评语\" // 分析评语（中文）\n" +
                "}\n\n" +
                "示例：\n" +
                "{\n" +
                "  \"score\": 95.0,\n" +
                "  \"accuracyScore\": 96.0,\n" +
                "  \"waveScore\": 94.0,\n" +
                "  \"operationScore\": 95.0,\n" +
                "  \"evaluation\": \"数据准确性较高，波形匹配良好，操作规范\"\n" +
                "}",
                studentDataBuilder.toString(),
                formatReferenceData(referenceData)
        );
    }

    // 辅助方法：格式化参考数据
    private static String formatReferenceData(Map<String, Object> referenceData) {
        StringBuilder sb = new StringBuilder("{\n");
        for (Map.Entry<String, Object> entry : referenceData.entrySet()) {
            sb.append("  \"").append(entry.getKey()).append("\": ");
            if (entry.getValue() instanceof String) {
                sb.append("\"").append(entry.getValue()).append("\"\n");
            } else {
                sb.append(entry.getValue()).append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // 辅助方法：添加字段到JSON
    private static void appendField(StringBuilder sb, String fieldName, Object value, Object referenceValue) {
        if (value != null) {
            sb.append("  \"").append(fieldName).append("\": ");
            if (value instanceof String) {
                sb.append("\"").append(value).append("\",  // 参考值: ");
            } else if (value instanceof Number) {
                sb.append(value).append(",  // 参考值: ");
            } else {
                sb.append("\"").append(value.toString()).append("\",  // 参考值: ");
            }
            
            if (referenceValue instanceof String) {
                sb.append("\"").append(referenceValue).append("\"\n");
            } else if (referenceValue instanceof Number) {
                sb.append(referenceValue).append("\n");
            } else {
                sb.append("\"").append(referenceValue.toString()).append("\"\n");
            }
        }
    }

}
