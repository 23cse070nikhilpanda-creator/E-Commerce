<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty product ? 'Add a New' : 'Edit'} Product</title>

    <style type="text/css">
        /* --- General Body & Font Styles --- */
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f4f7f9; /* Light gray background */
            color: #333;
            margin: 0;
            padding: 2rem;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            box-sizing: border-box;
        }

        /* --- Main Form Container --- */
        .content-box {
            width: 100%;
            max-width: 800px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
            overflow: hidden;
        }

        /* --- Form Header Section --- */
        .form-header {
            background-color: #4a69bd; /* A calming blue */
            color: #ffffff;
            padding: 1.5rem 2rem;
        }

        .form-header h1 {
            margin: 0;
            font-size: 1.5rem;
            font-weight: 600;
        }

        /* --- Form Layout & Grid --- */
        .form-container {
            padding: 2rem;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr; /* Single column for mobile */
            gap: 1.5rem; /* Space between form groups */
        }

        /* --- General Styling for Form Groups (label + input wrapper) --- */
        .form-group {
            display: flex;
            flex-direction: column;
        }

        /* Full-width elements like textarea */
        .form-group.full-width {
            grid-column: 1 / -1; /* This will be applied on larger screens */
        }

        /* --- Label Styles --- */
        label {
            margin-bottom: 0.5rem;
            font-weight: 600;
            font-size: 0.9rem;
            color: #555;
        }

        /* --- Common Styles for all Inputs, Selects, and Textareas --- */
        input[type="text"],
        input[type="number"],
        select,
        textarea {
            width: 100%;
            padding: 0.75rem;
            font-size: 1rem;
            border: 1px solid #ced4da;
            border-radius: 6px;
            background-color: #f8f9fa;
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
            box-sizing: border-box; /* Important for consistent sizing */
        }

        /* Enhanced Focus State for better UX */
        input[type="text"]:focus,
        input[type="number"]:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #4a69bd; /* Match header blue */
            box-shadow: 0 0 0 3px rgba(74, 105, 189, 0.2);
        }

        /* Specific styling for textarea */
        textarea {
            resize: vertical; /* Allow vertical resizing only */
            min-height: 120px;
        }

        /* --- Form Actions (Button container) --- */
        .form-actions {
            grid-column: 1 / -1; /* Always full-width */
            display: flex;
            justify-content: flex-end; /* Align button to the right */
            margin-top: 1rem;
        }

        /* --- Button Styles --- */
        .btn {
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            font-weight: 600;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.2s ease, transform 0.1s ease;
            text-align: center;
        }

        .btn-primary {
            background-color: #4a69bd; /* Primary action color */
            color: #ffffff;
        }

        .btn-primary:hover {
            background-color: #3b5496; /* Darker shade on hover */
        }

        .btn-primary:active {
            transform: translateY(1px); /* Simple click effect */
        }

        /* --- Responsive Design for Tablets and Desktops --- */
        @media (min-width: 768px) {
            .form-grid {
                /* Two equal columns on screens wider than 768px */
                grid-template-columns: 1fr 1fr;
            }
        }
    </style>
</head>
<body>

    <div class="content-box">
        <div class="form-header">
            <h1>${empty product ? 'Add a New' : 'Edit'} Product</h1>
        </div>
    
        <form action="dashboard" method="post" class="form-container">
            <%-- Determine action based on whether a product object exists --%>
            <c:if test="${not empty product}">
                <input type="hidden" name="action" value="updateProduct">
                <input type="hidden" name="id" value="${product.id}">
            </c:if>
            <c:if test="${empty product}">
                <input type="hidden" name="action" value="addProduct">
            </c:if>
    
            <div class="form-grid">
                <%-- Row 1 --%>
                <div class="form-group">
                    <label for="name">Product Name</label>
                    <input type="text" id="name" name="name" value="${product.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" name="categoryId" required>
                        <option value="">-- Select a Category --</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <%-- Pre-select the product's current category when editing --%>
                            <option value="${cat.id}" ${product.categoryId == cat.id ? 'selected' : ''}>
                                ${cat.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <%-- Row 2 --%>
                <div class="form-group">
                    <label for="price">Price ($)</label>
                    <input type="number" id="price" name="price" value="${product.price}" step="0.01" required>
                </div>
    
                <div class="form-group">
                    <label for="imageUrl">Image URL</label>
                    <input type="text" id="imageUrl" name="imageUrl" value="${product.imageUrl}">
                </div>
    
                <%-- Row 3 (Spans full width) --%>
                <div class="form-group full-width">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="5" required>${product.description}</textarea>
                </div>
    
                <%-- Actions Row --%>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Product</button>
                </div>
            </div>
        </form>
    </div>

</body>
</html>