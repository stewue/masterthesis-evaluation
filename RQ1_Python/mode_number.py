import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

name = ('1', '2', '3', '4')
percentage = (0.9731, 0.019, 0.0022, 0.0056)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.bar(name, percentage)
plt.ylabel('probability')
plt.xlabel('# modes')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\mode_number.pdf')
