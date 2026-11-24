# Release Notes

### HubSpot Marketing Extension 2.0.5

* Developer Name: Amruta Pawar
* Krista API Version: 1.0.118
* Global Catalog Version: GC-2025.02.02

### Enhancements

* **Centralized Error Handling**: Introduced `ErrorMessages` utility class for consistent, user-friendly error messages across the extension
* **Improved Error Messages**: All error messages now provide actionable guidance with context (IDs, parameters, details)
* **Enhanced Documentation**: Added comprehensive Architecture.md with Mermaid diagrams and DOCX documentation for LLM context
* **Better Code Quality**: Added Javadoc comments to utility classes and improved code structure
* **Security Improvements**: Error messages sanitized to avoid exposing sensitive information

### Code Improvements

* Created `ErrorMessages` utility class with categorized error messages (Authentication, Validation, API, Entity)
* Updated `HubspotExtension` to use centralized error messages
* Enhanced `StringValidator` with Javadoc and improved error handling
* Updated `EmailStore`, `TemplateStore`, `SubscriptionStore` with user-friendly error messages
* Improved `RestClient` error handling with specific messages for different HTTP status codes
* Enhanced `HubspotResource` OAuth error handling

### Documentation

* Added `Architecture.md` with comprehensive architecture documentation
* Created `HubSpot_Marketing_Extension_Documentation.docx` for LLM-optimized context
* Documented all error categories and troubleshooting steps
* Added design patterns and security considerations

### Known Issues

Not Available

---

