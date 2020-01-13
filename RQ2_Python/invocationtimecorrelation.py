import pandas as pd
from scipy import stats

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\invocationtimecorrelation.csv', delimiter=";")

corr = stats.spearmanr(data['changeRateCov'], data['meanCov'])
print("COV")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))
print("")

corr = stats.spearmanr(data['changeRateCi'], data['meanCi'])
print("CI")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))
print("")

corr = stats.spearmanr(data['changeRateDivergence'], data['meanDivergence'])
print("Divergence")
print("p: " + str(corr.pvalue))
print("correlation: " + str(corr.correlation))