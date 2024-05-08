# PDF Compare

![compare logo](compare.gif)


Are you tired of manually comparing PDF files in your daily automation tasks? If so, you're not alone. I am also encounter the same challenge of needing to compare PDFs not just by text, but also by their visual appearance, pixel by pixel, to ensure no detail is missed. I could not find any good FREE library which is working out of the box to compare the PDF files.

I've developed a Java library utilizing Apache PDFBox, licensed under Apache License, Version 3. This library allows users to compare PDF documents in Text/Image mode, highlighting differences. Additionally, it can extract images from PDFs and save PDF pages as images, offering versatile functionality for various tasks. With this tool, users can streamline PDF-related workflows, ensuring accuracy and efficiency. Its simplicity and effectiveness make it a valuable asset for developers and professionals alike.


## License

[MIT](https://choosealicense.com/licenses/mit/)

## Authors

- [@codewithsitangshu](https://github.com/codewithsitangshu/)

## Maven Dependency

Include the below dependency in your POM file.

```xml
<dependency>
  <groupId>com.org.codewithsitangshu</groupId>
  <artifactId>pdf-compare</artifactId>
  <version>1.0.0</version>
</dependency>
```

One can download jar for this [link](https://github-registry-files.githubusercontent.com/793082028/e8caff80-0ba2-11ef-9891-7d60f3a4d540?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240508%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240508T042945Z&X-Amz-Expires=300&X-Amz-Signature=6121d943b30f84ae34cd22cef07e626930807306ba1aa2611a0bdc1e2725fa05&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=793082028&response-content-disposition=filename%3Dpdf-compare-1.0.0.jar&response-content-type=application%2Foctet-stream) as well

## Setup Builder

First, we need to set up the Builder class for PDF comparison. We can use the default values, but if specific configurations are required to compare PDF files, then the Builder class needs to be configured accordingly.

**Configuration for Builder**

```java
private int startPage = 1; // Start page is first page
private int endPage = -1; // Last page is end page
private boolean isCompareAllPages = true; // comnpare all page of PDF
```

For Default Builder configuration here is the code.

```java
Config config = new Builder().build();
```

If you want to specify the start and end pages in your PDF files for comparison, then you need to use the following code to set up the Builder:

```java
Config config = new Builder()
    .setCompareAllPages(false)
    .setStartPage(2)
    .setEndPage(6)
    .build(); // Builder returns Config class object
```

If you want to compare specific page numbers in your PDF files, then you need to use the following code to set up the Builder:

```java
List<Integer> pages = Arrays.asList(5, 7); // Only compare page 5 and 7
Config config = new Builder()
    .setCompareAllPages(false)
    .setSpecificPages(pages)
    .build();
```

# Text Compare

To compare text, first, we need to set the compare mode as text:

```java
Comparator comparator = new Compare(config);
comparator.setCompareMode(CompareMode.TEXT); // Set CompareMode as Text
```

### Setup Config for Textual Validation

Now, we can set up a few configurations as per our needs (you can stick with the default config as well) in our Config class object that the Builder class returns.

**Trim White Space**

By default, it will not trim white spaces. But if you want to trim all white spaces, then you need to set the following configuration:

```java
config.setTrimWhiteSpace(true);
```

**Exclude String from Comparison**

If you want to exclude a specific string from your comparison, then it will not consider that string for comparison. Here is the configuration:

```java
config.setExcludeString("I am an Automation test engineer");
```

**Exclude List of Strings from Comparison**

If you want to exclude a list of strings from your comparison, then it will not consider those strings for comparison. Here is the configuration:

```java
List<String> stringToExclude = Arrays.asList("I love java", "I am an automation engineer","I love javascript");
config.setExcludeList(stringToExclude);
```

**Exclude Regular Expression from Comparison**

If you want to exclude strings based on a given regular expression from your comparison, then it will not consider those strings for comparison. Here is the configuration:

```java
config.setRegexToExclude("John Daugherty \\(\\d+\\)");
```

#### Compare and Assert

To compare two PDF files, use the following code:

```java
String expectedPdf = "src/test/resources/text-compare/expected.pdf";
String actualPdf = "src/test/resources/text-compare/actual.pdf";
ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);
```

You can then use the *ResultFormat*  object to assert your validation:

```java
//Assertion for text mismatch
boolean flag = assertThat(resultFormat).hasTextMismatch();
```

#### Print mismatch

You can utilize the *ResultFormat* to print all mismatches based on page and line numbers. Here is the code:

```java
//Print Differences
Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
StringBuilder allMismatchText = new StringBuilder();
allDifference.forEach((page, differences) -> {
    for (Difference<Object> difference : differences) {
        allMismatchText.append("Page ").append(page).append(":").append("\n\n");
        allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
        allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
        allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
    }
    allMismatchText.append("\n\n\n");
});
if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
    allMismatchText.append("No differences. Good to go.");
}
System.out.println(allMismatchText);
```
Here is the sample output

![text-compare.JPG](text-compare.JPG)




