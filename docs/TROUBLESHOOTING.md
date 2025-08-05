# CI/CD Troubleshooting Guide

## Common Issues and Solutions

### 1. SonarCloud Integration Issues

#### Problem: "Could not find a default branch for project"

```
Failed to execute goal org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar
Could not find a default branch for project with key 'bookstore-microservices'
```

**Solution**:

1. The SonarCloud project doesn't exist yet
2. Follow the [SonarCloud Setup Guide](./SONARCLOUD_SETUP.md)
3. The CI/CD pipeline now includes `continue-on-error: true` for SonarQube analysis
4. The workflow will skip SonarQube if `SONAR_TOKEN` secret is not configured

#### Quick Fix (Skip SonarCloud):

- Don't add the `SONAR_TOKEN` secret to GitHub
- The workflow will automatically skip SonarQube analysis

### 2. Package Declaration Warnings in VS Code

#### Problem: VS Code shows package declaration errors

```
The declared package "com.bookstore.book" does not match the expected package "main.java.com.bookstore.book"
```

**Explanation**:

- VS Code sometimes misinterprets Maven project structure
- The package declaration `com.bookstore.book` is CORRECT for Maven projects
- This is a VS Code display issue, not a real compilation error

**Solutions**:

1. **Ignore the warning** - it's a VS Code quirk, Maven will compile correctly
2. **Reload VS Code window**: `Cmd+Shift+P` → "Developer: Reload Window"
3. **Clean and rebuild**: Run `./mvnw clean compile` to verify everything works

### 3. Maven Build Issues

#### Problem: Maven compilation fails

**Solution**:

```bash
# Clean and rebuild all modules
./mvnw clean compile

# Run tests
./mvnw test

# Package all services
./mvnw package -DskipTests
```

### 4. Docker Build Issues

#### Problem: Docker builds fail during CI/CD

**Checklist**:

- ✅ Dockerfile exists in each service directory
- ✅ JAR files are built before Docker build
- ✅ Base image is accessible
- ✅ GitHub Container Registry permissions are set

### 5. GitHub Actions Secrets

#### Required Secrets for Full CI/CD:

| Secret Name             | Description                | Required            |
| ----------------------- | -------------------------- | ------------------- |
| `SONAR_TOKEN`           | SonarCloud analysis token  | Optional            |
| `AWS_ACCESS_KEY_ID`     | AWS deployment credentials | For AWS deployment  |
| `AWS_SECRET_ACCESS_KEY` | AWS deployment credentials | For AWS deployment  |
| `DOCKER_USERNAME`       | Docker Hub username        | For Docker registry |
| `DOCKER_PASSWORD`       | Docker Hub password        | For Docker registry |
| `SLACK_WEBHOOK`         | Slack notifications        | Optional            |

#### Missing Secrets Behavior:

- ❌ **Missing required secrets**: Deployment jobs will fail
- ⏭️ **Missing optional secrets**: Jobs will be skipped automatically

### 6. Workflow Permissions

#### Problem: GitHub Actions can't push Docker images

**Solution**:

1. Go to Repository Settings → Actions → General
2. Under "Workflow permissions", select:
   - "Read and write permissions"
   - "Allow GitHub Actions to create and approve pull requests"

### 7. Environment Protection Rules

#### Problem: Deployment jobs are blocked

**Solution**:

1. Go to Repository Settings → Environments
2. Configure protection rules for `staging` and `production`
3. Add required reviewers if needed
4. Set deployment branch restrictions

## Quick Diagnostic Commands

### Check Maven Setup:

```bash
./mvnw --version
./mvnw clean compile
```

### Check Docker Setup:

```bash
docker --version
docker build -t test-service ./user-service/
```

### Check Git Status:

```bash
git status
git log --oneline -5
```

### Verify Package Structure:

```bash
find . -name "*.java" -type f | head -10
```

## Workflow Status Indicators

### ✅ Healthy Pipeline:

- All jobs complete successfully
- Artifacts are uploaded
- Docker images are built and pushed
- Deployments complete without errors

### ⚠️ Partially Working:

- Some optional jobs fail (SonarQube, notifications)
- Core build and test jobs succeed
- Docker builds complete

### ❌ Broken Pipeline:

- Compilation fails
- Tests fail
- Required secrets missing
- Docker builds fail

## Getting Help

1. **Check GitHub Actions logs**: Go to Actions tab → Click on failed run → Review logs
2. **Review this troubleshooting guide**
3. **Check individual service logs**: Each microservice has its own build logs
4. **Verify secrets configuration**: Ensure all required secrets are set

## Prevention Tips

1. **Test locally first**: Always run `./mvnw clean verify` before pushing
2. **Use feature branches**: Don't push directly to main during development
3. **Monitor Actions**: Check GitHub Actions regularly for any failures
4. **Keep dependencies updated**: Use Dependabot for security updates

## Current CI/CD Status

✅ **Working Components**:

- Multi-module Maven build
- Unit testing across all services
- Docker image builds
- GitHub Container Registry integration
- Pull request validation
- Security scanning (OWASP)

⏳ **Optional Components** (require setup):

- SonarCloud integration
- AWS deployment
- Slack notifications
- Production deployment approvals
