#!/bin/bash

echo "🏗️ Building Book Store & Rental Platform..."

# Clean and compile all modules
echo "📦 Cleaning and compiling all modules..."
./mvnw clean compile -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "📋 Project Summary:"
    echo "- 🏗️ 11 modules successfully built"
    echo "- 🚪 API Gateway (Spring Cloud Gateway)"
    echo "- 👥 User Service (User management)"
    echo "- 📖 Book Service (Book catalog)"
    echo "- 📦 Inventory Service (Stock management)"
    echo "- 🛒 Order Service (Orders & purchases)"
    echo "- 📅 Rental Service (Rental lifecycle)"
    echo "- 📧 Notification Service (Email/SMS)"
    echo "- 📊 Report Service (Analytics)"
    echo "- 🔐 Auth Service (Authentication)"
    echo "- 🔗 Shared Libraries (Common DTOs)"
    echo ""
    echo "🚀 Ready for development!"
    echo "💡 Next: Run './setup-dev.sh' to start the development environment"
else
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
