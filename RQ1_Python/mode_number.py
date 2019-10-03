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
plt.savefig('export.png')
