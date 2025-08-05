# SonarCloud Setup Guide

## Overview
This guide helps you set up SonarCloud integration for continuous code quality analysis in your Book Store microservices project.

## Prerequisites
- GitHub repository with CI/CD pipeline
- SonarCloud account (free for public repositories)

## Step 1: Create SonarCloud Account
1. Go to [SonarCloud.io](https://sonarcloud.io)
2. Sign in with your GitHub account
3. Grant necessary permissions

## Step 2: Import Your Repository
1. Click "+" in the top right corner
2. Select "Analyze new project"
3. Choose your GitHub organization/account
4. Select `bookRenalApp` repository
5. Click "Set up"

## Step 3: Configure Project Settings
1. **Project Key**: Use `bookstore-microservices` (matches our CI/CD configuration)
2. **Organization**: Your GitHub username (automatically set)
3. **Main Branch**: `main`

## Step 4: Get SonarCloud Token
1. Go to SonarCloud → Your Avatar → My Account → Security
2. Generate a new token:
   - Name: `BookStore-CI-CD`
   - Type: `User Token`
   - Expiration: `No expiration` or `1 year`
3. Copy the generated token (save it securely!)

## Step 5: Add Token to GitHub Secrets
1. Go to your GitHub repository
2. Navigate to: Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Add:
   - **Name**: `SONAR_TOKEN`
   - **Value**: The token you copied from SonarCloud

## Step 6: Configure Quality Gate (Optional)
1. In SonarCloud, go to your project
2. Navigate to: Administration → Quality Gates
3. Either use the default "Sonar way" or create a custom quality gate
4. Set conditions like:
   - Coverage > 80%
   - Duplicated Lines < 3%
   - Maintainability Rating = A
   - Reliability Rating = A
   - Security Rating = A

## Step 7: Test the Integration
1. Push a commit to the `main` branch
2. Check the GitHub Actions workflow
3. Verify SonarQube analysis runs successfully
4. View results in SonarCloud dashboard

## Current Configuration
Our CI/CD pipeline is configured with:
- **Project Key**: `bookstore-microservices`
- **Host**: `https://sonarcloud.io`
- **Trigger**: Only on pushes to `main` branch
- **Failure Handling**: `continue-on-error: true` (won't fail the build)

## Quality Metrics Tracked
- **Coverage**: Unit test coverage percentage
- **Duplications**: Code duplication detection
- **Maintainability**: Code smells and technical debt
- **Reliability**: Bugs and potential issues
- **Security**: Security vulnerabilities and hotspots

## Troubleshooting

### Common Issues:
1. **"Could not find a default branch"**
   - Ensure project exists in SonarCloud
   - Verify project key matches configuration
   - Check if main branch is set correctly

2. **"Invalid token"**
   - Regenerate token in SonarCloud
   - Update GitHub secret with new token
   - Ensure token has project analysis permissions

3. **"Organization not found"**
   - Verify organization name in SonarCloud
   - Update organization parameter in CI/CD configuration

### Disable SonarCloud (Temporary)
If you want to disable SonarCloud analysis temporarily:
1. Remove the `SONAR_TOKEN` secret from GitHub
2. The workflow will skip SonarQube analysis automatically

## Benefits
✅ **Continuous Quality Monitoring**: Automated code analysis on every push
✅ **Technical Debt Tracking**: Identify and track code quality issues
✅ **Security Scanning**: Detect security vulnerabilities early
✅ **Coverage Reports**: Monitor test coverage across microservices
✅ **Quality Gates**: Enforce quality standards before deployment

## Integration Status
- ✅ CI/CD pipeline configured
- ⏳ SonarCloud project setup required
- ⏳ Token configuration needed
- ⏳ Quality gate configuration recommended

Once completed, you'll have comprehensive code quality analysis running automatically with every deployment!
